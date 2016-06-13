package com.sharkbaitextraordinaire.quakes.client;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.GeoJsonObject;
import org.geojson.Point;
import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharkbaitextraordinaire.quakes.EarthquakeFeedConfiguration;
import com.sharkbaitextraordinaire.quakes.core.Earthquake;
import com.sharkbaitextraordinaire.quakes.db.EarthquakeDAO;

public class EarthquakeFeedFetcher implements Runnable {

	private Client client;
	private final Logger logger = LoggerFactory.getLogger(EarthquakeFeedFetcher.class);
	private EarthquakeDAO earthquakedao;
	private EarthquakeFeedConfiguration configuration;
	private LinkedBlockingQueue<Earthquake> queue;
	private Counter duplicatedEarthquakes;
	
	public EarthquakeFeedFetcher(EarthquakeFeedConfiguration configuration, EarthquakeDAO earthquakeDAO, LinkedBlockingQueue<Earthquake> queue) {
		this.configuration = configuration;
		this.earthquakedao = earthquakeDAO;
		this.queue = queue;
		duplicatedEarthquakes = new Counter();
		new MetricRegistry().register("duplicatedEarthquakes", duplicatedEarthquakes);
	}

	@Override
	public void run() {
		logger.error("Earthquake feed fetcher client is RUNNING");
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		WebTarget target = client.target(configuration.getApiURL());
		Invocation.Builder invocationBuilder = target.request();
		Response response = invocationBuilder.get();

		int status = response.getStatus();
		if (status == 200) {

			String feedString = response.readEntity(String.class);
			try {
				FeatureCollection fc = mapper.readValue(feedString, FeatureCollection.class);
				for (Feature feature : fc.getFeatures()) {
					GeoJsonObject g = feature.getGeometry();
					if (g instanceof Point) {
						Point p = (Point)g;
						try {
							Earthquake quake = new Earthquake(feature);

							// Inserting should result in a duplicate key exception
							// if the quake already exists, so we can catch that and
							// continue with the next feature in the collection
							if (earthquakedao.findEarthquakeById(quake.getId()) == null) {
								earthquakedao.insert(quake); 
								queue.put(quake);
								logger.debug("queued a quake (" + queue.size() + ")");	
							} else {
								logger.debug("quake " + quake.getId() + " was already seen");
								duplicatedEarthquakes.inc();
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							logger.error("interrupted while adding to quake to queue");
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							logger.error("Illegal argument while creating earthquake?" + feature.getProperty("title"));
							e.printStackTrace();
						} catch (UnableToExecuteStatementException e) {
							// This means we got a duplicate key exception.
							// Skip this item from the collection since it is already in the system?
							continue;
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						logger.error("Earthquake " + feature.getProperty("title") + " did not have a suitable location");
					}
				}
				if (fc.getFeatures().isEmpty()) {
					logger.info("Earthquake feed contained zero earthquakes");
				}
			} catch (JsonParseException e) {
				logger.error(e.getMessage());
			} catch (JsonMappingException e) {
				logger.error(e.getMessage());
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Client getClient() {
		return this.client;
	}

}

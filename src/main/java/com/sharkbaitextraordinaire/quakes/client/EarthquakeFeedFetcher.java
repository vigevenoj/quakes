package com.sharkbaitextraordinaire.quakes.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.GeoJsonObject;
import org.geojson.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	public EarthquakeFeedFetcher(EarthquakeFeedConfiguration configuration, EarthquakeDAO earthquakeDAO) {
		this.configuration = configuration;
		this.earthquakedao = earthquakeDAO;
	}

	@Override
	public void run() {
		logger.error("Earthquake feed fetcher client is RUNNING");
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		WebTarget target = client.target("http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson");
		Invocation.Builder invocationBuilder = target.request();
		Response response = invocationBuilder.get();

		int status = response.getStatus();
		if (status == 200) {

			String feedString = response.readEntity(String.class);
			try {
				FeatureCollection fc = mapper.readValue(feedString, FeatureCollection.class);
				for (Feature feature : fc.getFeatures()) {
					GeoJsonObject g = feature.getGeometry();
					Map<String, Object> props = feature.getProperties();
					for (String s : props.keySet()) {
						logger.error(props.get(s).toString());
					}
					if (g instanceof Point) {
						Point p = (Point)g;
						
						logger.error("Earthquake..." + feature.getProperty("title"));
						logger.error(p.getCoordinates().getLatitude() + ", " + p.getCoordinates().getLongitude());
						// TODO Store this point into a database table
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
	
	private Earthquake convertFeatureToEarthquake(Feature f) {
		/*public Earthquake(double magnitude, String place, int earthquaketime, int update, int tz,
			String url, String detail, String felt, String cdi, String tsunami, int sig,
			String code, String ids, String type, String title, String id, Point location) {*/
		Earthquake quake = new Earthquake();
		quake.setMagnitude(f.getProperty("mag"));
		quake.setPlace(f.getProperty("place"));
		quake.setEarthquaketime(f.getProperty("time"));
		quake.setUpdate(f.getProperty("update"));
		quake.setTz(f.getProperty("tz"));
		quake.setUrl(f.getProperty("url"));
		quake.setDetail(f.getProperty("detail"));
		quake.setFelt(f.getProperty("felt"));
		quake.setCdi(f.getProperty("cdi"));
		quake.setTsunami(f.getProperty("tsunami"));
		quake.setSig(f.getProperty("sig"));
		quake.setCode(f.getProperty("code"));
		quake.setIds(f.getProperty("ids"));
		quake.setType(f.getProperty("type"));
		quake.setTitle(f.getProperty("title"));
		quake.setId(f.getProperty("id"));
		if (f.getGeometry() instanceof Point) {
			quake.setLocation((Point) f.getGeometry());
		} else {
			return null;
		}
		return quake;
	}

}

package com.sharkbaitextraordinaire.quakes.core;

import java.util.concurrent.LinkedBlockingQueue;

import org.geojson.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sharkbaitextraordinaire.quakes.EarthquakeAnalysisConfiguration;
import com.sharkbaitextraordinaire.quakes.db.LocationUpdateDAO;

import io.dropwizard.lifecycle.Managed;


public class EarthquakeAnalyzer implements Managed {
	
	private EarthquakeAnalysisConfiguration configuration;
	private LinkedBlockingQueue<Earthquake> queue;
	private LocationUpdateDAO locations;
	private final Logger logger = LoggerFactory.getLogger(EarthquakeAnalyzer.class);
	
	public EarthquakeAnalyzer(EarthquakeAnalysisConfiguration configuration, LinkedBlockingQueue<Earthquake> queue, LocationUpdateDAO locations) {
		this.configuration = configuration;
		this.queue = queue;
		this.locations = locations;
	}

	@Override
	public void start() {
		logger.warn("Starting earthquake analysis thread with " + queue.size() + " quakes queued");
		doWork();
	}
	
	public void stop() {
		
	}
	
	private void doWork() {
		try {
			Earthquake quake = queue.take();
			LocationUpdate location = locations.findLatest();
			
			Point locPoint = new Point(location.getLongitude(), location.getLatitude());
			double distance = Haversine.distance(quake.getLocation(), locPoint);
			if (configuration.getWorryDistanceThreshold() <= distance) {
				// send notification
				logger.error(quake.getTitle() + " is within WORRY threshold");
			} else if (configuration.getInterestDistanceThreshold() <= distance) {
				// log it
				logger.error(quake.getTitle() + " is not worrisome but is interesting. ID " + quake.getId());
			}  // We are neither worried nor interested in earthquakes this far away
		} catch (InterruptedException e) {
			logger.error("Interrupted while taking earthquake from queue");
		}
	}
	
}

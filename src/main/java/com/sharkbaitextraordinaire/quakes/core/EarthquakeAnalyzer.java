package com.sharkbaitextraordinaire.quakes.core;

import java.util.concurrent.LinkedBlockingQueue;

import org.geojson.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sharkbaitextraordinaire.quakes.EarthquakeAnalysisConfiguration;
import com.sharkbaitextraordinaire.quakes.db.LocationUpdateDAO;

import io.dropwizard.lifecycle.Managed;


public class EarthquakeAnalyzer implements Runnable {
	
	private EarthquakeAnalysisConfiguration configuration;
	private LinkedBlockingQueue<Earthquake> queue;
	private LocationUpdateDAO locations;
	private final Logger logger = LoggerFactory.getLogger(EarthquakeAnalyzer.class);
	
	public EarthquakeAnalyzer(EarthquakeAnalysisConfiguration configuration, LinkedBlockingQueue<Earthquake> queue, LocationUpdateDAO locations) {
		this.configuration = configuration;
		this.queue = queue;
		this.locations = locations;
	}

	public void run() {
		logger.warn("Starting earthquake analysis thread with " + queue.size() + " quakes queued");
		if (queue.isEmpty()) { 
			try {
				logger.warn("Sleeping for five seconds at startup...");
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		while(true) {
			try {
				Earthquake quake = queue.take();
				logger.debug("took an earthquake from the queue " + quake.getId() + " " + quake.getTitle());
				if (quake.getId() == null) {
					logger.error("Queue size is " + queue.size());
          continue;
				}
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
			} catch (Exception e) {
        logger.error("some kind or problem");
        e.printStackTrace();
      }
		}
	}
}

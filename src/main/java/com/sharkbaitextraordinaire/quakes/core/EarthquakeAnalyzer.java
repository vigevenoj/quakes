package com.sharkbaitextraordinaire.quakes.core;

import java.util.concurrent.LinkedBlockingQueue;

import org.geojson.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sharkbaitextraordinaire.quakes.EarthquakeAnalysisConfiguration;
import com.sharkbaitextraordinaire.quakes.client.outbound.pushover.SharkbaitPushoverClient;
import com.sharkbaitextraordinaire.quakes.db.LocationUpdateDAO;
import com.sharkbaitextraordinaire.quakes.db.MonitoredLocationDAO;

public class EarthquakeAnalyzer implements Runnable {

	private EarthquakeAnalysisConfiguration configuration;
	private LinkedBlockingQueue<Earthquake> queue;
	private LocationUpdateDAO locations;
	private MonitoredLocationDAO monitoredLocations;
	private SharkbaitPushoverClient pushover;
	private final Logger logger = LoggerFactory.getLogger(EarthquakeAnalyzer.class);

	public EarthquakeAnalyzer(EarthquakeAnalysisConfiguration configuration, 
			LinkedBlockingQueue<Earthquake> queue, 
			LocationUpdateDAO locations,
			MonitoredLocationDAO monitoredLocations,
			SharkbaitPushoverClient pushover) {
		this.configuration = configuration;
		this.queue = queue;
		this.locations = locations;
		this.monitoredLocations = monitoredLocations;
		this.pushover = pushover;
	}

	public void run() {
		logger.warn("Starting earthquake analysis thread with " + queue.size() + " quakes queued");
		if (queue.isEmpty()) { 
			try {
				logger.warn("Sleeping for five seconds at startup because earthquake queue is empty...");
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		while(true) {
			try {
				Earthquake quake = queue.take();
				logger.debug("took an earthquake from the queue: " + quake.getId() + ": " + quake.getTitle());
				if (quake.getId() == null) {
					logger.error("Queue size is " + queue.size());
					continue;
				}
				LocationUpdate location = locations.findLatest();

				Point locPoint = new Point(location.getLongitude(), location.getLatitude());
				analyzeQuake(quake, locPoint);
				for (MonitoredLocation ml : monitoredLocations.getAllMonitoredLocations()) {
					analyzeQuake(quake, ml.getLocation());
				}
				
			} catch (InterruptedException e) {
				logger.error("Interrupted while taking earthquake from queue");
			} catch (NullPointerException e) {
				// Most frequently because the latest location update is null
				// because we haven't gotten an update from the mqtt broker yet
			} catch (Exception e) {
				logger.error("some kind or problem");
				e.printStackTrace();
			}
		}
	}
	
	private void analyzeQuake(Earthquake quake, Point locationPoint) {
		double distance = Haversine.distance(quake.getLocation(), locationPoint);
		if (distance <= configuration.getWorryDistanceThreshold()) {
			// send notification
			logger.error(quake.getTitle() + " is within WORRY threshold at " + distance + "km");
			pushover.sendMessage(quake.getTitle(), quake.getUrl());
		} else if (distance <= configuration.getInterestDistanceThreshold()) {
			// log it
			logger.error(quake.getTitle() + "is not worrisome but is interesting at " 
			+ distance + "km. ID " + quake.getId() + ": " + quake.getUrl());
		} // No else block because we are neither worried nor interested in quakes this far away
	}
}

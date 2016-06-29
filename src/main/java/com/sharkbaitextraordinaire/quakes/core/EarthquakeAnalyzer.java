package com.sharkbaitextraordinaire.quakes.core;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.text.NumberFormat;

import org.geojson.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sharkbaitextraordinaire.quakes.EarthquakeAnalysisConfiguration;
import com.sharkbaitextraordinaire.quakes.SlackConfiguration;
import com.sharkbaitextraordinaire.quakes.client.outbound.pushover.SharkbaitPushoverClient;
import com.sharkbaitextraordinaire.quakes.db.LocationUpdateDAO;
import com.sharkbaitextraordinaire.quakes.db.MonitoredLocationDAO;

import allbegray.slack.SlackClientFactory;
import allbegray.slack.type.Channel;
import allbegray.slack.webapi.SlackWebApiClient;
import allbegray.slack.webapi.method.chats.ChatPostMessageMethod;

public class EarthquakeAnalyzer implements Runnable {

	private EarthquakeAnalysisConfiguration configuration;
	private LinkedBlockingQueue<Earthquake> queue;
	private LocationUpdateDAO locations;
	private MonitoredLocationDAO monitoredLocations;
	private SharkbaitPushoverClient pushover;
	private SlackConfiguration slackConfig;
	private Channel slackChannel;
	private SlackWebApiClient slackClient;
	private final Logger logger = LoggerFactory.getLogger(EarthquakeAnalyzer.class);
  private NumberFormat nf = NumberFormat.getIntegerInstance();

	public EarthquakeAnalyzer(EarthquakeAnalysisConfiguration configuration, 
			LinkedBlockingQueue<Earthquake> queue, 
			LocationUpdateDAO locations,
			MonitoredLocationDAO monitoredLocations,
			SharkbaitPushoverClient pushover,
			SlackConfiguration slackConfig) {
		this.configuration = configuration;
		this.queue = queue;
		this.locations = locations;
		this.monitoredLocations = monitoredLocations;
		this.pushover = pushover;
		this.slackConfig = slackConfig;
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
		setUpSlack();

		
		while(true) {
			try {
				Earthquake quake = queue.take();
				logger.debug("took an earthquake from the queue: " + quake.getId() + ": " + quake.getTitle());
				if (quake.getId() == null) {
					logger.error("Queue size is " + queue.size());
					continue;
				}
				LocationUpdate location = locations.findLatest();
				HashSet<MonitorableLocation> monitoredAndUpdatedLocations = new HashSet<MonitorableLocation>();
				monitoredAndUpdatedLocations.add(location);
				monitoredAndUpdatedLocations.addAll(monitoredLocations.getAllMonitoredLocations());
        MonitorableLocation closestLocation = determineClosestLocationToEarthquake(quake, monitoredAndUpdatedLocations);
        analyzeQuake(quake, closestLocation.getLocation(), closestLocation.getName());
			} catch (InterruptedException e) {
				logger.error("Interrupted while taking earthquake from queue");
			} catch (NullPointerException e) {
				// Most frequently because the latest location update is null
				// because we haven't gotten an update from the mqtt broker yet
			} catch (Exception e) {
				logger.error("some kind of problem");
				e.printStackTrace();
			}
		}
	}
	
	private void analyzeQuake(Earthquake quake, Point locationPoint, String locationName) {
		double distance = Haversine.distance(quake.getLocation(), locationPoint);

		if (distance <= configuration.getWorryDistanceThreshold()) {
			// send notification
			logger.error(quake.getTitle() + " is within WORRY threshold at " + nf.format(distance) + "km");
			pushover.sendMessage(quake.getTitle(), quake.getUrl());
			// TODO differentiate worrisome from interesting
			postToSlack("Worrisome" + quake.getTitle() + " is " + nf.format(distance) + "km from " + locationName 
					+ ". For more details, see <" + quake.getUrl() + ">");
		} else if (distance <= configuration.getInterestDistanceThreshold()) {
			// log it
			logger.error(quake.getTitle() + "is not worrisome but is interesting at " 
			+ nf.format(distance) + "km. ID " + quake.getId() + ": " + quake.getUrl());
			// TODO differentiate interesting from worrisome
			postToSlack("Interesting " + quake.getTitle() + " is " + nf.format(distance) + "km from " + locationName 
					+ ". For more details, see <" + quake.getUrl() + ">");
		} else {
			// Send it to slack test channel anyway
			postToSlack("Uninteresting " + quake.getTitle() + " is " + nf.format(distance) + "km from " + locationName 
					+ ". For more details, see <" + quake.getUrl() + ">");
		}
	}
	
	private void setUpSlack() {
		String token = slackConfig.getToken(); 
		String channelName = slackConfig.getChannelName();
		slackClient = SlackClientFactory.createWebApiClient(token);
		slackClient.auth();
		
		logger.debug("looking for slack channel named " + channelName);
		
		for (Channel c : slackClient.getChannelList()) {
			if (c.getName().equals(channelName)) {
				// can't join it because we are a bot user
				slackChannel = c;
				logger.warn("Using channel " + c.getName() + " with ID " + c.getId());
				break;
			}
		}
	}
	
	private String postToSlack(String message) {
		ChatPostMessageMethod postMessage = new ChatPostMessageMethod(slackChannel.getId(), message);
		postMessage.setUnfurl_links(true);
		postMessage.setUsername("woodhouse");
		postMessage.setAs_user(true);
		
		String ts = slackClient.postMessage(postMessage);
		logger.warn("response from slack post message: " + ts);
		return ts;
	}
	
	private MonitorableLocation determineClosestLocationToEarthquake(Earthquake quake, Collection<MonitorableLocation> locations) {
		MonitorableLocation closest = null;
		double closestDistance = Double.MAX_VALUE;
		for (MonitorableLocation location : locations) {
			double distance = Haversine.distance(quake.getLocation(), location.getLocation());
			if (distance < closestDistance) {
				closest = location;
			}
		}
		return closest;
	}
}

package com.sharkbaitextraordinaire.quakes.client.outbound.slack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;

import allbegray.slack.rtm.EventListener;
import allbegray.slack.webapi.SlackWebApiClient;

public class WoodhouseMessageEventListener implements EventListener {


	private final Logger logger = LoggerFactory.getLogger(WoodhouseMessageEventListener.class);
	SlackWebApiClient slackClient; // convenience for posting messages

	public WoodhouseMessageEventListener(SlackWebApiClient slackClient) {
		this.slackClient = slackClient;
	}

	@Override
	public void handleMessage(JsonNode message) {
		String messageText = message.get("text").textValue();
//		String messageChannel = message.get("channel").textValue();
//		String messageType = message.get("type").textValue();
//		String user = message.get("user").textValue();

		if (messageText.startsWith("woodhouse")) { // TODO or if this mentions the integration user
			logger.warn("Starting woodhouse integration command for '" + messageText + "'");
			handleWoodhouseIntegration(message);
		}
	}

	private void handleWoodhouseIntegration(JsonNode message) {
		// TODO this isn't written yet
		if (message.get("text").textValue().matches("(?i).*bus me home.*")) {
			handleBusMeHomeRequest(message);
		}
	}

	private void handleBusMeHomeRequest(JsonNode message) {
		// check current user, we might not have a location for them
		// if this user is location-enabled,
		isRequestingUserLocationEnabled(message.get("user").textValue());
		// get user's current location
		// TODO we need to get a LocationDAO to call findLatest on 
		// get trimet configuration
		// get home location
		// calculate haversine distance between user and home
		// tell the user how to get home
	}
	
	protected boolean isRequestingUserLocationEnabled(String username) {
		// TODO store this somewhere
		return true;
	}
}

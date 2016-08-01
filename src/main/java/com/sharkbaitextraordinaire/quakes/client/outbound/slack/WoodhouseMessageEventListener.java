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
		String messageChannel = message.get("channel").textValue();
		
		if (messageText.startsWith("woodhouse")) { // TODO or if this mentions the integration user
			logger.warn("Starting woodhouse integration command for '" + messageText + "'");
			handleWoodhouseIntegration(message);
		}
	}

	private void handleWoodhouseIntegration(JsonNode message) {
		// TODO this isn't written yet
	}
}

package com.sharkbaitextraordinaire.quakes.client.outbound.slack;

import allbegray.slack.rtm.EventListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;

public class MessageEventListener implements EventListener {
	
	private final Logger logger = LoggerFactory.getLogger(MessageEventListener.class);


	@Override
	public void handleMessage(JsonNode message) {
		String messageText = message.get("text").asText();
		logger.warn(messageText);
		
		if (messageText.startsWith("woodhouse")) {
			// TODO handle this via some method calls somewhere?
		} else if (messageText.startsWith("/lights")) {
			// TODO handle hue lighting integration code here
		}
	}

}

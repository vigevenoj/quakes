package com.sharkbaitextraordinaire.quakes.client.outbound.slack;

import allbegray.slack.rtm.EventListener;
import allbegray.slack.webapi.SlackWebApiClient;
import allbegray.slack.webapi.method.chats.ChatPostMessageMethod;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResourcesCache;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

public class MessageEventListener implements EventListener {
	
	private final Logger logger = LoggerFactory.getLogger(MessageEventListener.class);
	private SlackWebApiClient slackClient; // for posting messages...	
	private PHHueSDK huesdk;
	
	public MessageEventListener(SlackWebApiClient slackClient, PHHueSDK huesdk) {
		this.slackClient = slackClient;
		this.huesdk = huesdk;
	}

	@Override
	public void handleMessage(JsonNode message) {

		String messageText = message.get("text").textValue();
		String messageChannel = message.get("channel").textValue();
		
		if (messageText.startsWith("woodhouse")) { // TODO or if this mentions the integration user
			handleWoodhouseIntegration(message);
			logger.warn("Starting woodhouse integration command for '" + messageText + "'");
		} else if (messageText.startsWith("lights") || messageText.startsWith("/lights")) {
			handleLightingIntegration(message);
		} else {
			logger.warn(messageText);
		}
	}
	
	private void handleWoodhouseIntegration(JsonNode message) {
		// TODO this isn't written yet
	}
	
	private void handleLightingIntegration(JsonNode message) {
		String messageText = message.get("text").textValue();
		String messageType = message.get("type").textValue();
		String user = message.get("user").textValue();
		String messageChannel = message.get("channel").textValue();
		
		logger.warn("Starting lighting integration command for '" + messageText + "'");

		PHBridge bridge = huesdk.getSelectedBridge();
		PHBridgeResourcesCache cache = bridge.getResourceCache();
		
		if (messageText.startsWith("lights list")) {
			logger.error("Listing lights:");
			
			List<PHLight> lights = cache.getAllLights();
			StringBuilder sb = new StringBuilder();
			for (PHLight light : lights) {
				sb.append(light.getIdentifier()).append(" ").append(light.getName());
				sb.append("\n");
				logger.error(light.getIdentifier() + " " + light.getName());
			}
			
			ChatPostMessageMethod postMessage = new ChatPostMessageMethod(messageChannel, sb.toString());
			postMessage.setUnfurl_links(true);
			postMessage.setUsername("woodhouse");
			postMessage.setAs_user(true);
			
			String ts = slackClient.postMessage(postMessage);
				
		} else if (messageText.startsWith("lights on")) {
			logger.error("turning the lights on");
			PHLightState lightstate = new PHLightState();
			lightstate.setOn(true);
			bridge.setLightStateForDefaultGroup(lightstate);
			// TODO handle the response from setLightState and use that for a slack message
//			ChatPostMessageMethod postMessage = new ChatPostMessageMethod(messageChannel, "lighting isn't integrated yet");
//			postMessage.setUnfurl_links(true);
//			postMessage.setUsername("woodhouse");
//			postMessage.setAs_user(true);
//			
//			String ts = slackClient.postMessage(postMessage);
		} else if (messageText.startsWith("lights off")) {
			logger.error("turning the lights off");
			PHLightState lightstate = new PHLightState();
			lightstate.setOn(false);
			bridge.setLightStateForDefaultGroup(lightstate);
			// TODO handle the response from setLightState and use that for a slack message
//			ChatPostMessageMethod postMessage = new ChatPostMessageMethod(messageChannel, "lighting isn't integrated yet");
//			postMessage.setUnfurl_links(true);
//			postMessage.setUsername("woodhouse");
//			postMessage.setAs_user(true);
//			
//			String ts = slackClient.postMessage(postMessage);
		}
	}
}

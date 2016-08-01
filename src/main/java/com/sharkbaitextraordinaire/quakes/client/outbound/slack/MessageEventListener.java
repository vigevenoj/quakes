package com.sharkbaitextraordinaire.quakes.client.outbound.slack;

import allbegray.slack.rtm.EventListener;
import allbegray.slack.webapi.SlackWebApiClient;
import allbegray.slack.webapi.method.chats.ChatPostMessageMethod;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.philips.lighting.hue.listener.PHLightListener;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResource;
import com.philips.lighting.model.PHBridgeResourcesCache;
import com.philips.lighting.model.PHHueError;
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
		
		if (messageText.startsWith("lights ") || messageText.startsWith("light ")) {
			handleLightingIntegration(message);
		} else {
			logger.warn(messageText);
		}
	}
	
	private void handleLightingIntegration(JsonNode message) {
		String messageText = message.get("text").textValue();
//		String messageType = message.get("type").textValue();
//		String user = message.get("user").textValue();
		String messageChannel = message.get("channel").textValue();
		
		logger.info("Starting lighting integration command for '" + messageText + "'");

		PHBridge bridge = huesdk.getSelectedBridge();
		PHBridgeResourcesCache cache = bridge.getResourceCache();
		
		if (messageText.startsWith("lights list")) {
			logger.info("Listing lights:");
			
			List<PHLight> lights = cache.getAllLights();
			StringBuilder sb = new StringBuilder();
			for (PHLight light : lights) {
				sb.append(light.getIdentifier()).append(" ").append(light.getName());
				sb.append("\n");
				logger.info(light.getIdentifier() + " " + light.getName());
			}
			
			ChatPostMessageMethod postMessage = new ChatPostMessageMethod(messageChannel, sb.toString());
			postMessage.setUnfurl_links(true);
			postMessage.setUsername("woodhouse");
			postMessage.setAs_user(true);
			
			String ts = slackClient.postMessage(postMessage);
				
		} else if (messageText.startsWith("lights on")) {
			logger.debug("turning the lights on");
			PHLightState lightstate = new PHLightState();
			lightstate.setOn(true);
			bridge.setLightStateForDefaultGroup(lightstate);
		} else if (messageText.startsWith("lights off")) {
			logger.debug("turning the lights off");
			PHLightState lightstate = new PHLightState();
			lightstate.setOn(false);
			bridge.setLightStateForDefaultGroup(lightstate);
		}
		if (messageText.startsWith("light ")) {
			// handle single light:
			// first word is light, so drop it
			String[] line = messageText.split(" ", 2);
			// line[1] contains the rest of the string
			try {
				String[] args = line[1].split("\\s|,");
				String lightid = args[0];
				// Use this parse to see if we have an int or a string
				Integer.parseInt(args[0]);
				String onoff = args[1];
				PHLight light = bridge.getResourceCache().getLights().get(lightid);
				logger.debug("fetched light from cache, " + light.getIdentifier());
				toggleLightOnOff(light, onoff);
			} catch (NumberFormatException e) {
				// argument list doesn't start with a number so it's probably the name of a light
				String lightName;
				String[] args = line[1].split(",");
				lightName = args[0];
				String onoff = args[1].trim();
				List<PHLight> lights = bridge.getResourceCache().getAllLights();
				for (PHLight light : lights) {
					logger.debug("comparing " + lightName + " to " + light.getName());
					if (light.getName().trim().equalsIgnoreCase(lightName.trim())) {
						toggleLightOnOff(light, onoff);
						break;
					}
				}
			}
		}
	}
	
	private void toggleLightOnOff(PHLight light, String onoff) {
		logger.debug("Toggling " + light.getIdentifier() + " (" + light.getName() + ") " + onoff);
		PHLightState lightState = new PHLightState();
		if (onoff.equalsIgnoreCase("on")) {
			lightState.setOn(true);
		} else if (onoff.equals("off")){
			lightState.setOn(false);
		}
		PHHueSDK.getInstance().getSelectedBridge().updateLightState(light, lightState);
	}
	
	private String getLightIdFromName(String name) {
		List<PHLight> lights = huesdk.getInstance().getSelectedBridge().getResourceCache().getAllLights();
		for (PHLight light : lights) {
			if (light.getName().equals(name)) {
				return light.getIdentifier();
			}
		}
		return null;
	}
}

package com.sharkbaitextraordinaire.quakes.client.outbound.slack;

import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.sharkbaitextraordinaire.quakes.SlackConfiguration;

import allbegray.slack.SlackClientFactory;
import allbegray.slack.rtm.EventListener;
import allbegray.slack.rtm.SlackRealTimeMessagingClient;
import io.dropwizard.lifecycle.Managed;


public class SharkbaitSlackClient implements Managed {
	
	private SlackConfiguration slackConfig;
	private SlackRealTimeMessagingClient rtmClient;
	private final Logger logger = LoggerFactory.getLogger(SharkbaitSlackClient.class);
	private SharkbaitSlackRtmClientRunnable slackrtmclient;

	public SharkbaitSlackClient(SlackConfiguration slackConfig) {
		this.slackConfig = slackConfig;
	}

	@Override
	public void start() throws Exception {
		slackrtmclient = new SharkbaitSlackRtmClientRunnable();
		slackrtmclient.start();
	}

	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
		// TODO disconnect cleanly from slack
		// TODO stop Hue heartbeats
		// TODO disconnect cleanly from Hue
	}
	
	private class SharkbaitSlackRtmClientRunnable extends Thread {
		SharkbaitSlackRtmClientRunnable() {
			super("SharkbaitSlackRtmClientRunnable");
		}
		
		public void run() {
			logger.info("Slack rtm client integration starting up...");
			String token = slackConfig.getToken(); 
			
			rtmClient = SlackClientFactory.createSlackRealTimeMessagingClient(token);
			rtmClient.addListener("hello", new EventListener() {
				@Override
				public void handleMessage(JsonNode message) {
					logger.warn("handling hello message " + message.asText());
				}
			});
			rtmClient.addListener("message", new EventListener() {
				@Override
				public void handleMessage(JsonNode message) {
					logger.warn("handling message: " + message.asText());
				}
			});
			rtmClient.connect();
		}
	}
	
	
	// TODO this should be a utility method somewhere or its own class
	private static <T> Collector<T, ?, T> singletonCollector() {
	    return Collectors.collectingAndThen(
	            Collectors.toList(),
	            list -> {
	                if (list.size() != 1) {
	                    throw new IllegalStateException();
	                }
	                return list.get(0);
	            }
	    );
	}

}

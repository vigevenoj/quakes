package com.sharkbaitextraordinaire.quakes.client.outbound.pushover;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sharkbaitextraordinaire.quakes.SharkbaitPushoverClientConfiguration;

import net.pushover.client.PushoverClient;
import net.pushover.client.PushoverException;
import net.pushover.client.PushoverMessage;
import net.pushover.client.PushoverRestClient;

public class SharkbaitPushoverClient {

	private PushoverClient client = new PushoverRestClient();
	private SharkbaitPushoverClientConfiguration sharkbaitPushoverClientConfiguration;
	private final Logger logger = LoggerFactory.getLogger(SharkbaitPushoverClient.class);

	
	public SharkbaitPushoverClient(SharkbaitPushoverClientConfiguration sharkbaitPushoverClientConfiguration) {
		this.sharkbaitPushoverClientConfiguration = sharkbaitPushoverClientConfiguration;
	}
	
	public void sendMessage(String message) {
		try {
			client.pushMessage(PushoverMessage.builderWithApiToken(sharkbaitPushoverClientConfiguration.getApplicationToken())
					.setUserId(sharkbaitPushoverClientConfiguration.getUserToken())
					.setMessage(message)
					.build());
		} catch (PushoverException e) {
			logger.error(e.getMessage());
		}
	}
}

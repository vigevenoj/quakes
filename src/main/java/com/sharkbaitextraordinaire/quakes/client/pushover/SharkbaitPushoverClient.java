package com.sharkbaitextraordinaire.quakes.client.pushover;

import com.sharkbaitextraordinaire.quakes.SharkbaitPushoverClientConfiguration;

import net.pushover.client.PushoverClient;
import net.pushover.client.PushoverRestClient;

public class SharkbaitPushoverClient {

	private PushoverClient client = new PushoverRestClient();
	private SharkbaitPushoverClientConfiguration sharkbaitPushoverClientConfiguration;

	
	public SharkbaitPushoverClient(SharkbaitPushoverClientConfiguration sharkbaitPushoverClientConfiguration) {
		this.sharkbaitPushoverClientConfiguration = sharkbaitPushoverClientConfiguration;
	}
}

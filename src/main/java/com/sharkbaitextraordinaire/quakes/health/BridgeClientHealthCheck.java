package com.sharkbaitextraordinaire.quakes.health;

import com.codahale.metrics.health.HealthCheck;
import com.sharkbaitextraordinaire.quakes.client.inbound.bridges.BridgeClient;

public class BridgeClientHealthCheck extends HealthCheck {
	
	private final BridgeClient bridgeClient;
	
	public BridgeClientHealthCheck(BridgeClient bridgeClient) {
		this.bridgeClient = bridgeClient;
	}

	@Override
	protected Result check() throws Exception {
		if (bridgeClient.isConnected()) {
			return Result.healthy();
		} else {
			return Result.unhealthy("The bridge client is not connected to the API endpoint");
		}
	}

}

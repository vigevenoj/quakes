package com.sharkbaitextraordinaire.quakes.client;

import javax.ws.rs.client.Client;

public class EarthquakeFeedFetcher implements Runnable {
	
	private Client client;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	public Client getClient() {
		return this.client;
	}

}

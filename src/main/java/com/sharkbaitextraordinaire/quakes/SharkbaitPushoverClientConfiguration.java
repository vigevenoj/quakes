package com.sharkbaitextraordinaire.quakes;

public class SharkbaitPushoverClientConfiguration {

	private String applicationToken;
	private String userToken;
	
	public String getApiKey() {
		return this.applicationToken;
	}
	
	public void setApiKey(String apiKey) {
		this.applicationToken = apiKey;
	}
	
	public String getUserToken() {
		return this.userToken;
	}
	
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
}

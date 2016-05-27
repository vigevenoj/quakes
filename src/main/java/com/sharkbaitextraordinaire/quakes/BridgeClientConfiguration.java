package com.sharkbaitextraordinaire.quakes;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

public class BridgeClientConfiguration {
	
	String apiURL;
	String apiKey;
	
	@JsonProperty
	public String getApiURL() {
		return this.apiURL;
	}
	
	@JsonProperty
	public void setApiURL(String apiURL) {
		this.apiURL = apiURL;
	}
	
	@JsonProperty
	public String getApiKey() {
		return this.apiKey;
	}
	
	@JsonProperty
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

}

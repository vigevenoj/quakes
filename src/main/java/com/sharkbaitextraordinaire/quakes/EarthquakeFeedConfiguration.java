package com.sharkbaitextraordinaire.quakes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EarthquakeFeedConfiguration {
	
	String apiURL;
	
	@JsonProperty
	public String getApiURL() {
		return apiURL;
	}
	
	@JsonProperty
	public void setApiURL(String apiURL) {
		this.apiURL = apiURL;
	}

}

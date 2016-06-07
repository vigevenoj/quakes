package com.sharkbaitextraordinaire.quakes.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationUpdate {
	
	String _type;
	@JsonProperty("lat")
	double latitude;
	@JsonProperty("lon")
	double longitude;
	@JsonProperty("acc")
	String accuracy;
	@JsonProperty("batt")
	String battery; // percentage
	@JsonProperty("tst")
	Long timestamp; // timestamp in epoch
	String event; // optional, might not appear
	
	public LocationUpdate() { }
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public LocationUpdate(String _type, double lat, double lon, String acc, String batt, Long tst) {
		this._type = _type;
		this.latitude = lat;
		this.longitude = lon;
		this.accuracy = acc;
		this.battery = batt;
		this.timestamp = tst;
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public LocationUpdate(String _type, double lat, double lon, String acc, String batt, Long tst, String event) {
		this._type = _type;
		this.latitude = lat;
		this.longitude = lon;
		this.accuracy = acc;
		this.battery = batt;
		this.timestamp = tst;
		this.event = event;
	}
	
	public String get_type() {
		return _type;
	}
	
	public void set_type(String _type) {
		this._type = _type;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public String getAccuracy() {
		return accuracy;
	}
	
	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}
	
	public String getBattery() {
		return battery;
	}
	
	public void setBattery(String battery) {
		this.battery = battery;
	}
	
	public Long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getEvent() {
		return event;
	}
	
	public void setEvent(String event) {
		this.event = event;
	}
}

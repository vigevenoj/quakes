package com.sharkbaitextraordinaire.quakes.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class LocationUpdate {
	
	String _type;
	String latitude;
	String longitude;
	String accuracy;
	String battery; // percentage
	Long timestamp; // timestamp in epoch
//	String event; // optional, might not appear
	
	public LocationUpdate() { }
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public LocationUpdate(String _type, String lat, String lon, String acc, String batt, Long tst) {
		this._type = _type;
		this.latitude = latitude;
		this.longitude = longitude;
		this.accuracy = accuracy;
		this.battery = battery;
		this.timestamp = timestamp;
	}
	
	public String get_type() {
		return _type;
	}
	
	public void set_type(String _type) {
		this._type = _type;
	}
	
	public String getLongitude() {
		return longitude;
	}
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public String getLatitude() {
		return latitude;
	}
	
	public void setLatitude(String latitude) {
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
}

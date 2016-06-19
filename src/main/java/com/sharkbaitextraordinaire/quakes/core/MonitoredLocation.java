package com.sharkbaitextraordinaire.quakes.core;

import org.geojson.Point;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"location"})
public class MonitoredLocation {
	private Double latitude;
	private Double longitude;
	
	public MonitoredLocation() { }
	
	public MonitoredLocation(Double longitude, Double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public Double getLatitude() {
		return this.latitude;
	}
	
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	public double getLongitude() {
		return this.longitude;
	}
	
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public Point getLocation() {
		return new Point(longitude, latitude);
	}

	@Override
	public String toString() {
		return "MonitoredLocation [latitude=" + latitude + ", longitude=" + longitude + "]";
	}
}

package com.sharkbaitextraordinaire.quakes.core;

import org.geojson.Point;

public class MonitoredLocation {
	private double latitude;
	private double longitude;
	
	public MonitoredLocation() { }
	
	public double getLatitude() {
		return this.latitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getLongitude() {
		return this.longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public Point getLocation() {
		return new Point(longitude, latitude);
	}
}

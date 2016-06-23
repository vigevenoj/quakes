package com.sharkbaitextraordinaire.quakes.core;

import org.geojson.Point;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"location"})
public class MonitoredLocation {
	@NotEmpty
  @JsonProperty
	private Double latitude;
	@NotEmpty
  @JsonProperty
	private Double longitude;
  @JsonProperty
	private String name;
	
	public MonitoredLocation() { }
	
	public MonitoredLocation(String name, Double longitude, Double latitude) {
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public MonitoredLocation(Double longitude, Double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
		this.name = null;
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
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Point getLocation() {
		return new Point(longitude, latitude);
	}

	@Override
	public String toString() {
		return "MonitoredLocation [latitude=" + latitude + ", longitude=" + longitude + "]";
	}
}

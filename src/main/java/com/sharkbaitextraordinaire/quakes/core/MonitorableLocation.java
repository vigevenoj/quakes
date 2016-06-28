package com.sharkbaitextraordinaire.quakes.core;

import org.geojson.Point;

public interface MonitorableLocation {

	public String getName();
	
	public Point getLocation();
}

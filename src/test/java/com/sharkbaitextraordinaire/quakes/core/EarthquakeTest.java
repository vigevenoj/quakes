package com.sharkbaitextraordinaire.quakes.core;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import org.geojson.Feature;
import org.geojson.Point;
import org.junit.Test;

public class EarthquakeTest {
	
	@Test
	public void TestCreateEarthquakeWithFloatCDI() throws Exception {
		HashMap<String,Object> properties = new HashMap<String, Object>();
		properties.put("mag", 2.44d);
		properties.put("place", "6km W of Huntington Beach, CA");
		properties.put("time", 1465358999660l);
		properties.put("updated", 1465359724520l);
		properties.put("tz", -420);
		properties.put("url", "http://earthquake.usgs.gov/earthquakes/eventpage/ci37372583");
		properties.put("detail", "http://earthquake.usgs.gov/earthquakes/feed/v1.0/detail/ci37372583.geojson");
		properties.put("felt", 9);
		properties.put("cdi", 4.1d);
		properties.put("tsunami", 0);
		properties.put("sig", 95);
		properties.put("code", "37372583");
		properties.put("ids", ",ci37372583,");
		properties.put("type", "earthquake");
		properties.put("title", "M 2.4 - 6km W of Huntington Beach, CA");
		
		Point p = new Point(-118.0586667,33.6868333,13.81);
		
		Feature f = new Feature();
		f.setProperties(properties);
		f.setGeometry(p);
		f.setId("ci37372583");
		
		Earthquake quake = new Earthquake(f);
		assertNotNull(quake);
	}
	
	public void TestCreateEarthquakeWithMagnitudeOne() throws Exception {
		HashMap<String,Object> properties = new HashMap<String, Object>();
		properties.put("mag", 1);
		properties.put("place", "6km W of Huntington Beach, CA");
		properties.put("time", 1465358999660l);
		properties.put("updated", 1465359724520l);
		properties.put("tz", -420);
		properties.put("url", "http://earthquake.usgs.gov/earthquakes/eventpage/ci37372583");
		properties.put("detail", "http://earthquake.usgs.gov/earthquakes/feed/v1.0/detail/ci37372583.geojson");
		properties.put("felt", 9);
		properties.put("cdi", 4.1d);
		properties.put("tsunami", 0);
		properties.put("sig", 95);
		properties.put("code", "37372583");
		properties.put("ids", ",ci37372583,");
		properties.put("type", "earthquake");
		properties.put("title", "M 2.4 - 6km W of Huntington Beach, CA");
		
		Point p = new Point(-118.0586667,33.6868333,13.81);
		
		Feature f = new Feature();
		f.setProperties(properties);
		f.setGeometry(p);
		f.setId("ci37372583");
		
		Earthquake quake = new Earthquake(f);
		assertNotNull(quake);
	}
}

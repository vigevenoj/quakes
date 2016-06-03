package com.sharkbaitextraordinaire.quakes.core;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import static io.dropwizard.testing.FixtureHelpers.fixture;

import io.dropwizard.jackson.Jackson;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.geojson.*;
import org.junit.Test;

public class EarthquakeFeedTest {
	
	private ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	
	@Test
	public void testDeserializeEarthquakeFeedFixture() throws Exception {
		FeatureCollection featureCollection = mapper.readValue(fixture("fixtures/earthquakes-feed.json"), FeatureCollection.class);
		
		assertNotNull(featureCollection);
	}

  @Test
  public void deserializeSingleFeatureIntoEarthquake() throws Exception {
    FeatureCollection features = mapper.readValue(fixture("fixtures/earthquakes-feed.json"), FeatureCollection.class);

    assertTrue(features.iterator().hasNext());
    Feature feature = features.iterator().next();
    Earthquake quake = new Earthquake(feature);

    assertNotNull(quake);
  }
	
}

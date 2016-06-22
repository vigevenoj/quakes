package com.sharkbaitextraordinaire.quakes.core;

import org.junit.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jackson.Jackson;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

public class MonitoredLocationTest {
	
	private static final ObjectMapper mapper = Jackson.newObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
	
	@Test
	public void serializeUnnamedMonitoredLocation() throws Exception {
		final MonitoredLocation location = new MonitoredLocation(-122.634888, 45.517895);
		final String expected = mapper.writeValueAsString(
				mapper.readValue(fixture("fixtures/monitoredlocation.json"), MonitoredLocation.class));
		
		assertThat(mapper.writeValueAsString(location)).isEqualTo(expected);
	}
	
	@Test
	public void deserializeUnnamedFromJSON() throws Exception {
		final MonitoredLocation location = new MonitoredLocation(-122.634888, 45.517895);
		final MonitoredLocation readValue = mapper.readValue(fixture("fixtures/monitoredlocation.json"), MonitoredLocation.class);
		
		assertThat(readValue.getLatitude() == location.getLatitude());
		assertThat(readValue.getLongitude() == location.getLongitude());
	}
	
	@Test
	public void serializeNamedLocation() throws Exception {
		final MonitoredLocation location = new MonitoredLocation("hood", -121.695728, 45.37476);
		final MonitoredLocation readValue = mapper.readValue(fixture("fixtures/namedMonitoredLocation.json"), MonitoredLocation.class);
		
		assertThat(readValue.getLatitude() == location.getLatitude());
		assertThat(readValue.getLongitude() == location.getLongitude());
		assertThat(readValue.getName() == location.getName());
		
	}
}

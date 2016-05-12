package com.sharkbaitextraordinaire.quakes.core;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;

import org.junit.Ignore;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

public class LocationUpdateTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    

    @Test
    public void serializeToJSON() throws Exception {
        final LocationUpdate update = new LocationUpdate("location", "45.5367495", "-122.6217988", "50.0", "81", 1437014122L);

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/locationupdate.json"), LocationUpdate.class));

        assertThat(MAPPER.writeValueAsString(update)).isEqualTo(expected);
    }

    @Test
    public void deserializeFromJSON() throws Exception {
        final LocationUpdate update = new LocationUpdate("location", "45.5367495", "-122.6217988", "50.0", "81", 1437014122L);
        LocationUpdate readValue = MAPPER.readValue(fixture("fixtures/locationupdate.json"), LocationUpdate.class);

    	assertNotNull(readValue);
    	assertThat(readValue.get_type()).isEqualTo(update.get_type());
    	assertThat(readValue.getLatitude()).isEqualTo(update.getLatitude());
    	assertThat(readValue.getLongitude()).isEqualTo(update.getLongitude());
    	assertThat(readValue.getAccuracy()).isEqualTo(update.getAccuracy());
    	assertThat(readValue.getBattery()).isEqualTo(update.getBattery());
    	assertThat(readValue.getTimestamp()).isEqualTo(update.getTimestamp());
    }
    
    @Test
    @Ignore
    public void deserializeWithEnterEventFromJSON() throws Exception {
    	final LocationUpdate update = new LocationUpdate("location", "45.5367495", "-122.6217988", "50.0", "94", 1443486427L);
    	LocationUpdate readValue = MAPPER.readValue(fixture("fixtures/LocationUpdateWithEnterEvent.json"), LocationUpdate.class);
    	
    	assertNotNull(readValue);
    	assertThat(readValue.get_type()).isEqualTo(update.get_type());
    	assertThat(readValue.getLatitude()).isEqualTo(update.getLatitude());
    	assertThat(readValue.getLongitude()).isEqualTo(update.getLongitude());
    	assertThat(readValue.getAccuracy()).isEqualTo(update.getAccuracy());
    	assertThat(readValue.getBattery()).isEqualTo(update.getBattery());
    	assertThat(readValue.getTimestamp()).isEqualTo(update.getTimestamp());
    	
    }
}

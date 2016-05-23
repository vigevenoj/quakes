package com.sharkbaitextraordinaire.quakes.client.bridges;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;
import java.util.HashMap;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.sharkbaitextraordinaire.quakes.core.BridgeUpdate;


public class BridgeUpdateTest {
	
	private ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	
	@Test
	public void testDeserializateBridgeUpdateFeature() throws Exception {
		TypeReference<HashMap<String,Object>> typeRef = new TypeReference<HashMap<String,Object>>() {};
		HashMap<String,Object> o = mapper.readValue(fixture("fixtures/bridges/bridgedata.json"), typeRef);
		
		assertNotNull(o.get("changed"));
		assertTrue(o.get("changed") instanceof HashMap<?,?>);
		assertNotNull(o.get("burnside"));
		assertTrue(o.get("burnside") instanceof HashMap<?,?>);
	}

	@Test
	public void testMapper() throws Exception {
		BridgeUpdate bu = mapper.readValue(fixture("fixtures/bridges/bridgedata.json"), BridgeUpdate.class);
		assertEquals(bu.getChangedBridge().toString(), BridgeUpdate.HAWTHORNE);
		assertEquals(bu.getChangedItem().toString(), "status");
		
	}
}
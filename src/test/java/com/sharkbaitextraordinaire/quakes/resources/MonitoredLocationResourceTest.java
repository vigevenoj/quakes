package com.sharkbaitextraordinaire.quakes.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.ClassRule;
import org.junit.Ignore;

import com.sharkbaitextraordinaire.quakes.core.MonitoredLocation;
import com.sharkbaitextraordinaire.quakes.db.MonitoredLocationDAO;

import io.dropwizard.testing.junit.ResourceTestRule;

public class MonitoredLocationResourceTest {

	private static final MonitoredLocationDAO dao = mock(MonitoredLocationDAO.class);
	
	private final MonitoredLocation location = new MonitoredLocation(-122.634888, 45.517895);
	private List<MonitoredLocation> allLocations = (List<MonitoredLocation>) Collections.singletonList(location);
	
	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder()
	.addResource(new MonitoredLocationResource(dao))
	.build();
	
	@Before
	public void setup() {
		when(dao.getAllMonitoredLocations()).thenReturn(allLocations);
	}
	
	@After
	public void tearDown() {
		reset(dao);
	}
	
	@Ignore
	@Test
	public void testGetAllMonitoredLocations() {
		assertThat(resources.client().target("/monitored").request().get(MonitoredLocation.class)).isEqualTo(allLocations);
		verify(dao).getAllMonitoredLocations();
	}
	
}

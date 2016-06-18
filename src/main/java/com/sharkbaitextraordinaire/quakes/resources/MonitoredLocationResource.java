package com.sharkbaitextraordinaire.quakes.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.sharkbaitextraordinaire.quakes.core.MonitoredLocation;
import com.sharkbaitextraordinaire.quakes.db.MonitoredLocationDAO;

@Produces(MediaType.APPLICATION_JSON)
public class MonitoredLocationResource {

	private final MonitoredLocationDAO dao;
	
	public MonitoredLocationResource(MonitoredLocationDAO dao) {
		this.dao = dao;
	}
	
	@GET
	@Timed
	public List<MonitoredLocation> getMonitoredLocations() {
		return dao.getAllMonitoredLocations();
	}
	
	@GET
	@Timed
	public void addMonitoredLocation(MonitoredLocation location) {
		dao.insert(location);
	}
	
}

package com.sharkbaitextraordinaire.quakes.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.codahale.metrics.annotation.Timed;
import com.sharkbaitextraordinaire.quakes.core.MonitoredLocation;
import com.sharkbaitextraordinaire.quakes.db.MonitoredLocationDAO;

import io.dropwizard.validation.Validated;

@Path("/monitored")
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
	
	@POST
	@Timed
	public Response addMonitoredLocation(@Validated MonitoredLocation location) {
		// TODO calculate if this location overlaps with another location
		// For now, use a simple lat/lon equality check (which would work better in the db)
		boolean duplicate = false;
		for (MonitoredLocation m : dao.getAllMonitoredLocations()) {
			if (m.getLocation() == location.getLocation()) {
				duplicate = true;
			}
		}
		if (duplicate == false) {
			dao.insert(location);
			return Response.status(Status.CREATED).build();
		} else {
			return Response.status(Status.CONFLICT).build();
		}
	}
	
}

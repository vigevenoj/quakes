package com.sharkbaitextraordinaire.quakes.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sharkbaitextraordinaire.quakes.core.LocationUpdate;
import com.sharkbaitextraordinaire.quakes.db.LocationUpdateDAO;

@Path(value = "/location")
@Produces(MediaType.APPLICATION_JSON)
public class LocationUpdateResource {
	
	private final LocationUpdateDAO locationdao;
	
	public LocationUpdateResource(LocationUpdateDAO locationdao) {
		this.locationdao = locationdao;
	}

	@GET
	public LocationUpdate getLocation() {
		return locationdao.findLatest();
	}
}

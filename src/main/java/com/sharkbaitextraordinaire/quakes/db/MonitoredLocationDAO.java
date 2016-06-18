package com.sharkbaitextraordinaire.quakes.db;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

import com.sharkbaitextraordinaire.quakes.core.MonitoredLocation;

public interface MonitoredLocationDAO {
	
	@SqlUpdate("create table monitoredlocations (lat decimal(9,6), lon decimal(9,6))")
	void createTableIfNotExists();
	
	@SqlQuery("select latitude, longitude from monitoredlocations")
	List<MonitoredLocation> getAllMonitoredLocations();
	
	@SqlUpdate("insert into monitoredlocations (latitude, longitude) values (:latitude, :longitude)")
	void insert(@Bind("latitude") double latitude, @Bind("longitude") double longitude );
	
	@SqlUpdate("insert into monitoredlocations (latitude, longitude) values (l.latitude, l.longitude)")
	void insert(@BindBean MonitoredLocation l);
}

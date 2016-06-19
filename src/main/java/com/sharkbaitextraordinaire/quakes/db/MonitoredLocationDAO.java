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
	
	@SqlQuery("select longitude, latitude from monitoredlocations")
	List<MonitoredLocation> getAllMonitoredLocations();
	
	@SqlUpdate("insert into monitoredlocations (longitude, latitude) values (:longitude, latitude)")
	void insert(@Bind("latitude") double latitude, @Bind("longitude") double longitude );
	
	@SqlUpdate("insert into monitoredlocations (longitude, latitude) values (l.longitude, l.latitude)")
	void insert(@BindBean MonitoredLocation l);
}

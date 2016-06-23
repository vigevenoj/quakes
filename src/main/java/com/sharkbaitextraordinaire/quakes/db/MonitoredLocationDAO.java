package com.sharkbaitextraordinaire.quakes.db;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import com.sharkbaitextraordinaire.quakes.core.MonitoredLocation;
import com.sharkbaitextraordinaire.quakes.core.MonitoredLocationMapper;

@RegisterMapper(MonitoredLocationMapper.class)
public interface MonitoredLocationDAO {
	
	@SqlUpdate("create table monitoredlocations (latitude decimal(9,6), longitude decimal(9,6), name varchar(32) )")
	void createTableIfNotExists();
	
	@SqlQuery("select name, longitude, latitude from monitoredlocations")
	List<MonitoredLocation> getAllMonitoredLocations();
	
	@SqlUpdate("insert into monitoredlocations (name, longitude, latitude) values (:longitude, latitude)")
	void insert(@Bind("name") String name, @Bind("latitude") double latitude, @Bind("longitude") double longitude );
	
	@SqlUpdate("insert into monitoredlocations (name, longitude, latitude) values (:l.name, :l.longitude, :l.latitude)")
	void insert(@BindBean("l") MonitoredLocation l);
}

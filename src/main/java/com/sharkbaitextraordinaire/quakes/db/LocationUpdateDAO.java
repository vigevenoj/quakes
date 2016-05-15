package com.sharkbaitextraordinaire.quakes.db;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import com.sharkbaitextraordinaire.quakes.core.LocationUpdateMapper;
import com.sharkbaitextraordinaire.quakes.core.LocationUpdate;

@RegisterMapper(LocationUpdateMapper.class)
public interface LocationUpdateDAO {
	
	@SqlUpdate("create table locationupdates (_type varchar(10), lat varchar(12), lon varchar(12), acc varchar(8) tst bigint, batt varchar(8), event varchar(32) )")
	void createTableIfNotExists();
	
	@SqlQuery("select _type, lat, lon, acc, tst, batt, event from locationupdates order by tst asc")
	List<LocationUpdate> findAll();
	
	@SqlQuery("select _type, lat, lon, acc, tst, batt from locationupdates order by tst desc limit 1")
	LocationUpdate findLatest();
	
	@SqlUpdate("insert into locationupdates (_type, lat, lon, acc, tst, batt) values (:_type, :lat, :lon, :acc, :tst, :batt)")
	void insert(@Bind("_type") String _type, @Bind("lat") String lat, @Bind("lon") String lon, @Bind("acc") String acc, @Bind("tst") Long tst, @Bind("batt") String batt);
	

}

package com.sharkbaitextraordinaire.quakes.db;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

public interface EarthquakeDAO {

	@SqlUpdate("create table earthquakes "
			+ "magnitude float(2,2), "
			+ "place varchar(64), " 
			+ "time int, "
			+ "update int, "
			+ "tz  int, "
			+ "url varchar(256), "
			+ "detail varchar(256), "
			+ "felt varchar(10), "
			+ "cdi varchar(10), "
			+ "tsunami varchar(8), "
			+ "sig int, "
			+ "code varchar(24), "
			+ "idsvarchar(24), "
			+ "type varchar(32), "
			+ "title varchar(256), "
			+ "id varchar(32)")
	void createTableIfNotExists();
	

	void insert();
}

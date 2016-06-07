package com.sharkbaitextraordinaire.quakes.db;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import com.sharkbaitextraordinaire.quakes.core.Earthquake;

public interface EarthquakeDAO {

	@SqlUpdate("create table earthquakes ("
			+ "magnitude float(2), "
			+ "place varchar(64), " 
			+ "earthquaketime bigint, "
			+ "updatetime bigint, "
			+ "tz int, "
			+ "url varchar(256), "
			+ "detail varchar(256), "
			+ "felt int, "
			+ "cdi int, "
			+ "tsunami int, "
			+ "sig int, "
			+ "code varchar(24), "
			+ "ids varchar(24), "
			+ "type varchar(32), "
			+ "title varchar(256), "
			+ "id varchar(32),"
			+ "longitude decimal(9,6), "
			+ "latitude decimal(9,6)"
			+ ")")
	void createTableIfNotExists();
	
	@SqlUpdate("insert into earthquakes "
			+ "(magnitude, place, earthquaketime, updatetime, tz, detail, felt, cdi, tsunami, sig, code, ids, type, title, id, longitude, latitude)"
			+ " values "
			+ "(:magnitude, :place, :earthquaketime, :updatetime, :tz, :felt, :cdi, :tsunami, :sig, :code, :ids, :type, :title, :id, :longitude, :latitude)")
	void insert(@Bind("magnitude") double magnitude,
			@Bind("place") String place, 
			@Bind("earthquaketime") long earthquaketime,
			@Bind("updatetime") long updatetime,
			@Bind("tz") int tz,
			@Bind("url") String url,
			@Bind("detail") String detail,
			@Bind("felt") int felt,
			@Bind("cdi") int cdi,
			@Bind("tsunami") int tsunami,
			@Bind("sig") int sig,
			@Bind("code") String code,
			@Bind("ids") String ids,
			@Bind("type") String types,
			@Bind("title") String title,
			@Bind("id") String id,
			@Bind("longitude") double longitude,
			@Bind("latitude") double latitude);
	
	@SqlUpdate("insert into earthquakes "
			+ "(magnitude, place, earthquaketime, updatetime, tz, detail, felt, cdi, tsunami, sig, code, ids, type, title, id, longitude, latitude)"
			+ " values "
			+ "(:e.magnitude, :e.place, :e.earthquaketime, :e.updatetime, :e.tz, :e.felt, :e.cdi, :e.tsunami, :e.sig, :e.code, :e.ids, :e.type, :e.title, :e.id, e.location.getCoordinates().getLongitude(), :e.location.getCoordinates().getLatitude())")
	void insert(@BindBean Earthquake e);
	
	@SqlQuery("select magnitude, place, earthquaketime, updatetime, tz, detail, felt, cdi, tsunami, sig, code, ids, type, title, id, longitude, latitude from earthquakes order by earthquaketime desc")
	List<Earthquake> findAll();
	
	@SqlQuery("select magnitude, place, earthquaketime, updatetime, tz, detail, felt, cdi, tsunami, sig, code, ids, type, title, id, longitude, latitude from earthquakes where id = :id")
	Earthquake findEarthquakeById(@Bind("id") String id);
	
}

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
			+ "felt varchar(10), "
			+ "cdi varchar(10), "
			+ "tsunami int, "
			+ "sig int, "
			+ "code varchar(24), "
			+ "ids varchar(24), "
			+ "type varchar(32), "
			+ "title varchar(256), "
			+ "id varchar(32) )")
	void createTableIfNotExists();
	
	@SqlUpdate("insert into earthquakes "
			+ "(magnitude, place, earthquaketime, updatetime, tz, detail, felt, cdi, tsunami, sig, code, ids, type, title, id)"
			+ " values "
			+ "(:magnitude, :place, :earthquaketime, :updatetime, :tz, :felt, :cdi, :tsunami, :sig, :code, :ids, :type, :title, :id)")
	void insert(@Bind("magnitude") double magnitude,
			@Bind("place") String place, 
			@Bind("earthquaketime") long earthquaketime,
			@Bind("updatetime") long updatetime,
			@Bind("tz") int tz,
			@Bind("url") String url,
			@Bind("detail") String detail,
			@Bind("felt") String felt,
			@Bind("cdi") String cdi,
			@Bind("tsunami") int tsunami,
			@Bind("sig") int sig,
			@Bind("code") String code,
			@Bind("ids") String ids,
			@Bind("type") String types,
			@Bind("title") String title,
			@Bind("id") String id);
	
	@SqlUpdate("insert into earthquakes "
			+ "(magnitude, place, earthquaketime, updatetime, tz, detail, felt, cdi, tsunami, sig, code, ids, type, title, id)"
			+ " values "
			+ "(:e.magnitude, :e.place, :e.earthquaketime, :e.updatetime, :e.tz, :e.felt, :e.cdi, :e.tsunami, :e.sig, :e.code, :e.ids, :e.type, :e.title, :e.id)")
	void insert(@BindBean Earthquake e);
	
	@SqlQuery("select magnitude, place, earthquaketime, updatetime, tz, detail, felt, cdi, tsunami, sig, code, ids, type, title, id from earthquakes order by earthquaketime desc")
	List<Earthquake> findAll();
	
}

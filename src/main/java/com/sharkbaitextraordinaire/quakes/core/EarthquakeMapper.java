package com.sharkbaitextraordinaire.quakes.core;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class EarthquakeMapper implements ResultSetMapper<Earthquake> {

	@Override
	public Earthquake map(int index, ResultSet resultSet, StatementContext statementContext) throws SQLException { 
		return new Earthquake(resultSet.getDouble("magnitude"),
				resultSet.getString("place"),
				resultSet.getLong("earthquaketime"),
				resultSet.getLong("updatetime"),
				resultSet.getInt("tz"),
				resultSet.getString("url"),
				resultSet.getString("detail"),
				resultSet.getInt("felt"),
				resultSet.getDouble("cdi"),
				resultSet.getInt("tsunami"),
				resultSet.getInt("sig"),
				resultSet.getString("code"),
				resultSet.getString("ids"),
				resultSet.getString("type"),
				resultSet.getString("title"),
				resultSet.getString("id"),
				resultSet.getDouble("longitude"),
				resultSet.getDouble("latitude"));

	}

}

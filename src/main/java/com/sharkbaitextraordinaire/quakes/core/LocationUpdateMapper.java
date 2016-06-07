package com.sharkbaitextraordinaire.quakes.core;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationUpdateMapper implements ResultSetMapper<LocationUpdate> {

	@Override
	public LocationUpdate map(int index, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		String event = "";
		try { 
			event = resultSet.getString("event");
			return new LocationUpdate(
					resultSet.getString("_type"),
					resultSet.getDouble("lat"),
					resultSet.getDouble("lon"),
					resultSet.getString("acc"),
					resultSet.getString("batt"),
					resultSet.getLong("tst"),
					event);
		} catch (SQLException e) {
			return new LocationUpdate(
					resultSet.getString("_type"),
					resultSet.getDouble("lat"),
					resultSet.getDouble("lon"),
					resultSet.getString("acc"),
					resultSet.getString("batt"),
					resultSet.getLong("tst"));
		}
	}

}

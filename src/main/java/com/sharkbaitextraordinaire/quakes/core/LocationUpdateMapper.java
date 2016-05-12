package com.sharkbaitextraordinaire.quakes.core;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationUpdateMapper implements ResultSetMapper<LocationUpdate> {

	@Override
	public LocationUpdate map(int indxe, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new LocationUpdate(
				resultSet.getString("_type"),
				resultSet.getString("latitude"),
				resultSet.getString("longitude"),
				resultSet.getString("accuracy"),
				resultSet.getString("battery"),
				resultSet.getLong("tst"));
	}

}

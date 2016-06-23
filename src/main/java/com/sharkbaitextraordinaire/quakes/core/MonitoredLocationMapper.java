package com.sharkbaitextraordinaire.quakes.core;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MonitoredLocationMapper implements ResultSetMapper<MonitoredLocation> {

  @Override
  public MonitoredLocation map(int index, ResultSet resultSet, StatementContext statementContext) throws SQLException {
    String name = "";
    try {
      name = resultSet.getString("name");
      return new MonitoredLocation(name, resultSet.getDouble("longitude"), resultSet.getDouble("latitude"));
    } catch (SQLException e) {
      return new MonitoredLocation(resultSet.getDouble("longitude"), resultSet.getDouble("latitude"));
    }
  }
}

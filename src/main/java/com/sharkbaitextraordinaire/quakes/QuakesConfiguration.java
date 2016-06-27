package com.sharkbaitextraordinaire.quakes;

import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.db.DataSourceFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

import com.sharkbaitextraordinaire.quakes.OwntracksMqttClientConfiguration;
import com.sharkbaitextraordinaire.quakes.core.MonitoredLocation;

public class QuakesConfiguration extends Configuration {

  @JsonProperty
  private OwntracksMqttClientConfiguration owntracksMqttClientConfiguration = new OwntracksMqttClientConfiguration();
  
  @JsonProperty
  private JerseyClientConfiguration jerseyClientConfiguration = new JerseyClientConfiguration();
  
  @JsonProperty
  private BridgeClientConfiguration bridgeClientConfiguration = new BridgeClientConfiguration();
  
  @JsonProperty
  private EarthquakeFeedConfiguration earthquakeFeedConfiguration = new EarthquakeFeedConfiguration();
  
  @JsonProperty
  private SharkbaitPushoverClientConfiguration sharkbaitPushoverClientConfiguration = new SharkbaitPushoverClientConfiguration();
  
  @JsonProperty
  private EarthquakeAnalysisConfiguration earthquakeAnalysisConfiguration = new EarthquakeAnalysisConfiguration();
  
  @JsonProperty("initialMonitoredLocations")
  private List<MonitoredLocation> initialMonitoredLocations;
  
  @JsonProperty("slackConfigurations")
  private List<SlackConfiguration> slackConfigurations;
  
  @Valid
  @NotNull
  @JsonProperty
  private DataSourceFactory database = new DataSourceFactory();

  public OwntracksMqttClientConfiguration getOwntracksMqttClientConfiguration() {
    return owntracksMqttClientConfiguration;
  }
  
  public JerseyClientConfiguration getJerseyClientConfiguration() {
	  return jerseyClientConfiguration;
  }
  
  public EarthquakeFeedConfiguration getEarthquakeFeedConfiguration() {
	  return earthquakeFeedConfiguration;
  }
  
  public BridgeClientConfiguration getBridgeClientConfiguration() {
	  return bridgeClientConfiguration;
  }
  
  @JsonProperty("database")
  public DataSourceFactory getDataSourceFactory() {
      return database;
  }
  
  public EarthquakeAnalysisConfiguration getEarthquakeAnalysisConfiguration() {
	  return earthquakeAnalysisConfiguration;
  }

  public SharkbaitPushoverClientConfiguration getSharkbaitPushoverClientConfiguration() {
	  return sharkbaitPushoverClientConfiguration;
  }
  
  public List<MonitoredLocation> getInitialMonitoredLocations() {
    return initialMonitoredLocations;
  }
  
  public List<SlackConfiguration> getSlackConfigurations() {
	  return slackConfigurations;
  }
}

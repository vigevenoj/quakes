package com.sharkbaitextraordinaire.quakes;

import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;

import com.sharkbaitextraordinaire.quakes.OwntracksMqttClientConfiguration;

public class QuakesConfiguration extends Configuration {

  @JsonProperty
  private OwntracksMqttClientConfiguration owntracksMqttClientConfiguration = new OwntracksMqttClientConfiguration();
  
  @JsonProperty
  private JerseyClientConfiguration jerseyClientConfiguration = new JerseyClientConfiguration();
  
  @JsonProperty
  private BridgeClientConfiguration bridgeClientConfiguration = new BridgeClientConfiguration();
  
  @JsonProperty
  private EarthquakeFeedConfiguration earthquakeFeedConfiguration = new EarthquakeFeedConfiguration();

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
}

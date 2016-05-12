package com.sharkbaitextraordinaire.quakes;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;

import com.sharkbaitextraordinaire.quakes.OwntracksMqttClientConfiguration;

public class QuakesConfiguration extends Configuration {

  @JsonProperty
  private OwntracksMqttClientConfiguration owntracksMqttClientConfiguration = new OwntracksMqttClientConfiguration();

  public OwntracksMqttClientConfiguration getOwntracksMqttClientConfiguration() {
    return owntracksMqttClientConfiguration;
  }
}

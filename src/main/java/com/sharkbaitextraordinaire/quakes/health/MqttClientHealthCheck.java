package com.sharkbaitextraordinaire.quakes.health;

import com.codahale.metrics.health.HealthCheck;
import com.sharkbaitextraordinaire.quakes.client.inbound.mqtt.OwntracksMqttClient;

public class MqttClientHealthCheck extends HealthCheck {
  
  private final OwntracksMqttClient client;

  public MqttClientHealthCheck(OwntracksMqttClient client) {
    this.client = client;
  }

  @Override
  protected Result check() throws Exception {
    if (client.getClient().isConnected()) {
      return Result.healthy();
    } else {
      return Result.unhealthy("Disconnected from MQTT broker " + client.getClient().getServerURI());
    }
  }
}

package com.sharkbaitextraordinaire.quakes;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.client.JerseyClientBuilder; 

import com.sharkbaitextraordinaire.quakes.health.MqttClientHealthCheck;

import javax.ws.rs.client.Client;

import com.sharkbaitextraordinaire.quakes.client.OwntracksMqttClient;


public class QuakesApplication extends Application<QuakesConfiguration> {

    public static void main(final String[] args) throws Exception {
        new QuakesApplication().run(args);
    }

    @Override
    public String getName() {
        return "Quakes";
    }

    @Override
    public void initialize(final Bootstrap<QuakesConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final QuakesConfiguration configuration,
                    final Environment environment) {

        final Managed owntracksMqttClient = new OwntracksMqttClient(configuration.getOwntracksMqttClientConfiguration());
        environment.lifecycle().manage(owntracksMqttClient);
        
        final Client client = new JerseyClientBuilder(environment)
        		.using(configuration.getJerseyClientConfiguration())
        		.build(getName());

        environment.healthChecks().register("broker", new MqttClientHealthCheck((OwntracksMqttClient) owntracksMqttClient) );
    }

}

package com.sharkbaitextraordinaire.quakes;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.lifecycle.setup.ScheduledExecutorServiceBuilder;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.jdbi.DBIFactory;

import com.sharkbaitextraordinaire.quakes.health.MqttClientHealthCheck;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;

import org.skife.jdbi.v2.DBI;

import com.sharkbaitextraordinaire.quakes.client.EarthquakeFeedFetcher;
import com.sharkbaitextraordinaire.quakes.client.bridges.BridgeClient;
import com.sharkbaitextraordinaire.quakes.client.mqtt.OwntracksMqttClient;
import com.sharkbaitextraordinaire.quakes.db.EarthquakeDAO;
import com.sharkbaitextraordinaire.quakes.db.LocationUpdateDAO;


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
    	
    	final DBI dbi = new DBIFactory().build(environment, configuration.getDataSourceFactory(), "database");
    	final LocationUpdateDAO ludao = dbi.onDemand(LocationUpdateDAO.class);
    	ludao.createTableIfNotExists();
    	
    	final EarthquakeDAO eqdao = dbi.onDemand(EarthquakeDAO.class);
    	eqdao.createTableIfNotExists();

        final Managed owntracksMqttClient = new OwntracksMqttClient(configuration.getOwntracksMqttClientConfiguration(), ludao);
        environment.lifecycle().manage(owntracksMqttClient);
        
        final Client earthquakeClient = new JerseyClientBuilder(environment)
        		.using(configuration.getJerseyClientConfiguration())
        		.build(getName());
        
        environment.jersey().register(earthquakeClient);
        
        ScheduledExecutorServiceBuilder sesBuilder = environment.lifecycle().scheduledExecutorService("earthquakefeedfetcher");
        ScheduledExecutorService quakefeedservice = sesBuilder.build();
        EarthquakeFeedFetcher earthquakeFeedFetcher = new EarthquakeFeedFetcher(configuration.getEarthquakeFeedConfiguration(), eqdao);
        earthquakeFeedFetcher.setClient(earthquakeClient);
        quakefeedservice.scheduleAtFixedRate(earthquakeFeedFetcher, 0, 10, TimeUnit.MINUTES);
        quakefeedservice.schedule(earthquakeFeedFetcher, 0, TimeUnit.SECONDS);        
        
        final Managed bridgeClient = new BridgeClient(configuration.getBridgeClientConfiguration());
        environment.lifecycle().manage(bridgeClient);
        

        environment.healthChecks().register("broker", new MqttClientHealthCheck((OwntracksMqttClient) owntracksMqttClient) );
    }

}

package com.sharkbaitextraordinaire.quakes;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.lifecycle.setup.ScheduledExecutorServiceBuilder;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.jdbi.DBIFactory;

import com.sharkbaitextraordinaire.quakes.health.BridgeClientHealthCheck;
import com.sharkbaitextraordinaire.quakes.health.MqttClientHealthCheck;
import com.sharkbaitextraordinaire.quakes.resources.LocationUpdateResource;
import com.sharkbaitextraordinaire.quakes.resources.MonitoredLocationResource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;

import org.skife.jdbi.v2.DBI;

import com.codahale.metrics.MetricRegistry;
import com.sharkbaitextraordinaire.quakes.client.inbound.bridges.BridgeClient;
import com.sharkbaitextraordinaire.quakes.client.inbound.mqtt.OwntracksMqttClient;
import com.sharkbaitextraordinaire.quakes.client.inbound.usgs.EarthquakeFeedFetcher;
import com.sharkbaitextraordinaire.quakes.client.outbound.pushover.SharkbaitPushoverClient;
import com.sharkbaitextraordinaire.quakes.core.Earthquake;
import com.sharkbaitextraordinaire.quakes.core.EarthquakeAnalyzer;
import com.sharkbaitextraordinaire.quakes.db.EarthquakeDAO;
import com.sharkbaitextraordinaire.quakes.db.LocationUpdateDAO;
import com.sharkbaitextraordinaire.quakes.db.MonitoredLocationDAO;


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
    	quakeQueue = new LinkedBlockingQueue<Earthquake>();
    }

    @Override
    public void run(final QuakesConfiguration configuration,
                    final Environment environment) {
    	
    	final MetricRegistry metrics = new MetricRegistry();
    	
    	final DBI dbi = new DBIFactory().build(environment, configuration.getDataSourceFactory(), "database");
    	final LocationUpdateDAO ludao = dbi.onDemand(LocationUpdateDAO.class);
    	ludao.createTableIfNotExists();
    	
    	final EarthquakeDAO eqdao = dbi.onDemand(EarthquakeDAO.class);
    	eqdao.createTableIfNotExists();
    	
    	final MonitoredLocationDAO mldao = dbi.onDemand(MonitoredLocationDAO.class);
    	mldao.createTableIfNotExists();
    	
    	environment.jersey().register(new MonitoredLocationResource(mldao));

        final Managed owntracksMqttClient = new OwntracksMqttClient(configuration.getOwntracksMqttClientConfiguration(), ludao, metrics);
        environment.lifecycle().manage(owntracksMqttClient);
        
        final Client earthquakeClient = new JerseyClientBuilder(environment)
        		.using(configuration.getJerseyClientConfiguration())
        		.build(getName());
        
        environment.jersey().register(earthquakeClient);
        
        ScheduledExecutorServiceBuilder quakeFetcherExecutorBuilder = environment.lifecycle().scheduledExecutorService("earthquakefeedfetcher");
        ScheduledExecutorService quakefeedservice = quakeFetcherExecutorBuilder.build();
        EarthquakeFeedFetcher earthquakeFeedFetcher = new EarthquakeFeedFetcher(configuration.getEarthquakeFeedConfiguration(), eqdao, quakeQueue);
        earthquakeFeedFetcher.setClient(earthquakeClient);
        quakefeedservice.scheduleAtFixedRate(earthquakeFeedFetcher, 0, 10, TimeUnit.MINUTES);
        quakefeedservice.schedule(earthquakeFeedFetcher, 0, TimeUnit.SECONDS);        
        
        final Managed bridgeClient = new BridgeClient(configuration.getBridgeClientConfiguration());
        environment.lifecycle().manage(bridgeClient);
        
        SharkbaitPushoverClient pushoverClient = new SharkbaitPushoverClient(configuration.getSharkbaitPushoverClientConfiguration());
  
        ExecutorService analysisService = environment.lifecycle().executorService("quake-analysis").maxThreads(1).minThreads(1).build();
        EarthquakeAnalyzer earthquakeAnalyzer = new EarthquakeAnalyzer(configuration.getEarthquakeAnalysisConfiguration(), quakeQueue, ludao, pushoverClient);
        analysisService.submit(earthquakeAnalyzer);
        
        environment.jersey().register(new LocationUpdateResource(ludao));

        environment.healthChecks().register("broker", new MqttClientHealthCheck((OwntracksMqttClient) owntracksMqttClient) );
        environment.healthChecks().register("bridges", new BridgeClientHealthCheck((BridgeClient) bridgeClient));
        
    }
    
    private LinkedBlockingQueue<Earthquake> quakeQueue;

}

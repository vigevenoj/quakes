package com.sharkbaitextraordinaire.quakes.client.bridges;

import static io.dropwizard.testing.FixtureHelpers.fixture;

import java.io.IOException;
import java.lang.annotation.Target;
import java.util.HashMap;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.geojson.Point;
import org.glassfish.jersey.media.sse.EventInput;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharkbaitextraordinaire.quakes.BridgeClientConfiguration;
import com.sharkbaitextraordinaire.quakes.core.BridgeUpdate;

import io.dropwizard.lifecycle.Managed;

public class BridgeClient implements Managed {

	private Client client;
	private final Logger logger = LoggerFactory.getLogger(BridgeClient.class);
	private BridgeClientConfiguration bridgeClientConfiguration;
	public static final String targetUrl = "https://api.multco.us/bridges/sse";

	public BridgeClient(BridgeClientConfiguration bridgeClientConfiguration) {
		this.bridgeClientConfiguration = bridgeClientConfiguration;
	}

	@Override
	public void start() throws Exception {
		logger.info("Bridge lift status client starting up...");
		logger.info("Using " + bridgeClientConfiguration.getApiURL() + " as target");

		client = ClientBuilder.newBuilder().register(SseFeature.class).build();
		WebTarget target = client.target(
				bridgeClientConfiguration.getApiURL() + "?access_token=" + bridgeClientConfiguration.getApiKey());

		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		EventInput eventInput = target.request().get(EventInput.class);
		while (!eventInput.isClosed()) {
			final InboundEvent inboundEvent = eventInput.read();
			if (inboundEvent == null) {
				// connection has been closed
				// TODO fail healthcheck?
				break;
			}
			if (inboundEvent.getName() == "null") {
				// this is a keep-alive message, and should be seen every 20 seconds
			} else if (inboundEvent.getName() == "bridge data") {
				// Bridge Data:
				// A json object consisting of possible updates to bridge statuses
				logger.info(inboundEvent.getName() + "; " + inboundEvent.readData(String.class));
				BridgeUpdate bu = mapper.readValue(inboundEvent.readData(), BridgeUpdate.class);
				String changedBridge = bu.getChangedBridge();
				String bridgeEventTime = "";
				String event = "";
						
				if (bu.getChangedItem().equals("status")) {
					bridgeEventTime = bu.getBridgeUpdates().get(changedBridge).getUpTime().toString();
					event = "raised";
				} else {
					bridgeEventTime = bu.getBridgeUpdates().get(changedBridge).getLastFive().get(0).getDownTime().toString();
					event = "lowered";
				}
				logger.error(changedBridge + " " + event + " at " + bridgeEventTime);

			} else {
				logger.info(inboundEvent.getName() + "; " + inboundEvent.readData(String.class));
			}
		}

	}

	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub

	}
}

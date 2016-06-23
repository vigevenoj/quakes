package com.sharkbaitextraordinaire.quakes.client.inbound.bridges;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.media.sse.EventInput;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharkbaitextraordinaire.quakes.BridgeClientConfiguration;
import com.sharkbaitextraordinaire.quakes.core.multcobridges.BridgeUpdate;
import com.sharkbaitextraordinaire.quakes.core.multcobridges.SingleBridgeUpdate;

import io.dropwizard.lifecycle.Managed;

public class BridgeClient implements Managed {

	private Client client;
	private final Logger logger = LoggerFactory.getLogger(BridgeClient.class);
	private BridgeClientConfiguration bridgeClientConfiguration;
	public static final String targetUrl = "https://api.multco.us/bridges/sse";
	private AtomicBoolean isOpen = new AtomicBoolean(false);
	private BridgeClientRunnable bcr;

	public BridgeClient(BridgeClientConfiguration bridgeClientConfiguration) {
		this.bridgeClientConfiguration = bridgeClientConfiguration;
	}

	@Override
	public void start() throws Exception {
		bcr = new BridgeClientRunnable();
		bcr.start();
	}

	@Override
	public void stop() throws Exception {
		// unimplemented
	}
	
	public boolean isConnected() {
		return isOpen.get();
	}
	
	
	private class BridgeClientRunnable extends Thread {
		BridgeClientRunnable() {
			super("BridgeClientRunnable");
		}
		
		public void run() {
			logger.info("Bridge lift status client starting up...");
			logger.info("Using " + bridgeClientConfiguration.getApiURL() + " as target");

			client = ClientBuilder.newBuilder().register(SseFeature.class).build();
			WebTarget target = client.target(
					bridgeClientConfiguration.getApiURL() + "?access_token=" + bridgeClientConfiguration.getApiKey());


			EventInput eventInput = target.request().get(EventInput.class);
			while (!eventInput.isClosed()) {
				InboundEvent inboundEvent = eventInput.read();
				isOpen.set(true);
				if (inboundEvent == null) {
					// connection has been closed
					// TODO fail healthcheck?
					isOpen.set(false);
					break;
				}
				parseBridgeUpdate(inboundEvent);
			}
		}

    private void parseBridgeUpdate(InboundEvent inboundEvent) {
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      if (inboundEvent.getName().equals("null") || inboundEvent.getName() == null) {
					// this is a keep-alive message, and should be seen every 20 seconds
					logger.debug("Bridge event (name is null): '" + inboundEvent.getName() + "'");
				} else if (inboundEvent.getName().equals("bridge data")) {
					// Bridge Data:
					// A json object consisting of possible updates to bridge statuses
					logger.info(inboundEvent.getName() + "; " + inboundEvent.readData(String.class));
					BridgeUpdate bu;
					try {
						bu = mapper.readValue(inboundEvent.readData(), BridgeUpdate.class);
						String changedBridge = bu.getChangedBridge();
						String bridgeEventTime = "";
						String event = "";
								
						if (bu.getChangedItem().equals("status")) {
							if (changedBridge != null && !("".equals(changedBridge))) {
								logger.warn("Getting bridge update for " + changedBridge);
								SingleBridgeUpdate sbu = bu.getBridgeUpdates().get(changedBridge);
								if (sbu == null) {
									logger.warn("Bridge update for " + changedBridge + " was null");
								} else {
									logger.warn("Up time for bridge is " + sbu.getUpTime());
									bridgeEventTime = sbu.getUpTime().toString();
								}
//								bridgeEventTime = bu.getBridgeUpdates().get(changedBridge).getUpTime().toString();
							} else {
								logger.warn("Changed bridge was null");
							}
							event = "raised";
						} else {
							bridgeEventTime = bu.getBridgeUpdates().get(changedBridge).getLastFive().get(0).getDownTime().toString();
							event = "lowered";
						}
						logger.error(changedBridge + " " + event + " at " + bridgeEventTime);
					} catch (JsonParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JsonMappingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					

				} else {
					// This event's name is "null;" and is the keepalive?
					logger.debug("bridge event (in else): '" + inboundEvent.getName() + "'");
          logger.debug(inboundEvent.toString());
				}
    }
	}


}

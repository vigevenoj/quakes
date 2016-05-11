package com.sharkbaitextraordinaire.quakes.client;

import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sharkbaitextraordinaire.quakes.OwntracksMqttClientConfiguration;

import io.dropwizard.lifecycle.Managed;

public class OwntracksMqttClient implements MqttCallback, Managed {
	
	private final Logger logger = LoggerFactory.getLogger(OwntracksMqttClient.class);
	
	private OwntracksMqttClientConfiguration owntracksMqttClientConfiguration;
	
	MqttClient client;
	MqttConnectOptions connectionOptions;
	
	public OwntracksMqttClient(OwntracksMqttClientConfiguration owntracksMqttClientConfiguration) {
		this.owntracksMqttClientConfiguration = owntracksMqttClientConfiguration;
	}
	
	public MqttClient getClient() {
		return client;
	}

	@Override
	/** Start the Managed owntracks client*/
	public void start() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	/** Stop the Managed owntracks client */
	public void stop() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectionLost(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
		// TODO Auto-generated method stub
		
	}

}

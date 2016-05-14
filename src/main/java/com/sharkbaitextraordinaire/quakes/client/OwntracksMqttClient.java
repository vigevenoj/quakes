package com.sharkbaitextraordinaire.quakes.client;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharkbaitextraordinaire.quakes.OwntracksMqttClientConfiguration;
import com.sharkbaitextraordinaire.quakes.core.LocationUpdate;

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
		String clientID = owntracksMqttClientConfiguration.getClientID();
		String brokerUrl = owntracksMqttClientConfiguration.getBrokerUrl();
		
		connectionOptions = new MqttConnectOptions();
		connectionOptions.setKeepAliveInterval(30);
		connectionOptions.setUserName(owntracksMqttClientConfiguration.getUserName());
		connectionOptions.setPassword(owntracksMqttClientConfiguration.getPassword().toCharArray());
		
		try {
			InputStream truststoreInput = Thread.currentThread().getContextClassLoader().getResourceAsStream(owntracksMqttClientConfiguration.getTrustStore());
			setSSLFactories(truststoreInput);
			truststoreInput.close();
			
			connectionOptions.setSocketFactory(SSLContext.getDefault().getSocketFactory());
		} catch (NoSuchAlgorithmException nsae) {
			logger.error("No such algorithm exception", nsae);
		} catch (KeyManagementException kme) {
			kme.printStackTrace();
		} catch (CertificateException ce) {
			ce.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		try {
			client = new MqttClient(brokerUrl, clientID);
			client.setCallback(this);
			client.connect(connectionOptions);
			
			if (client.isConnected()) {
				logger.error("connected to mqtt broker for owntracks");
				MqttTopic topic = client.getTopic(owntracksMqttClientConfiguration.getTopic());
				
				int subQoS = 0;
				client.subscribe(topic.getName(), subQoS);
			} else { 
				logger.error("NOT CONNECTED to mqtt broker");
			}
		} catch (MqttException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	/** Stop the Managed owntracks client */
	public void stop() throws Exception {
		try {
			client.disconnect();
			client.close();
		} catch (MqttException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void connectionLost(Throwable arg0) {
		logger.error("Connection to MQTT broker was lost");
		logger.info(arg0.getMessage());
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// this is a no-op because we are only subscribing, not publishing
		
	}

	@Override
	public void messageArrived(String topicString, MqttMessage message) throws Exception {
		String payload = new String(message.getPayload());
		logger.info("new message from broker:");
		logger.info(payload);
		
		try {
			// TODO don't allocate a new ObjectMapper every time
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			LocationUpdate update = mapper.readValue(payload, LocationUpdate.class);
			logger.info(update.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void setSSLFactories(InputStream trustStream) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, KeyManagementException {
		KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
		char[] trustStorePassword = null; 
		if (null == owntracksMqttClientConfiguration.getTrustStorePassword() 
				|| "".equals(owntracksMqttClientConfiguration.getTrustStorePassword())
				|| owntracksMqttClientConfiguration.getTrustStorePassword().isEmpty()) {
			trustStorePassword = null;
		} else {
			owntracksMqttClientConfiguration.getTrustStorePassword().toCharArray();
		}
		trustStore.load(trustStream, trustStorePassword);
		
		TrustManagerFactory trustFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		trustFactory.init(trustStore);
		
		TrustManager[] trustManagers = trustFactory.getTrustManagers();
		
		SSLContext sslContext = SSLContext.getInstance("SSL"); // TODO is this right?
		sslContext.init(null, trustManagers, null);
		SSLContext.setDefault(sslContext);
	}

}

package com.sharkbaitextraordinaire.quakes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OwntracksMqttClientConfiguration {
    @JsonProperty
    public String getBrokerUrl() {
        return brokerUrl;
    }
 
    @JsonProperty
    public void setBrokerUrl(String brokerUrl) {
        this.brokerUrl = brokerUrl;
    }
 
    @JsonProperty
    public String getClientID() {
        return clientID;
    }
 
    @JsonProperty
    public void setClientID(String clientID) {
        this.clientID = clientID;
    }
 
    @JsonProperty
    public String getUserName() {
        return userName;
    }
 
    @JsonProperty
    public void setUserName(String userName) {
        this.userName = userName;
    }
 
    @JsonProperty
    public String getPassword() {
        return password;
    }
 
    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
 
    @JsonProperty
    public String getSslProtocol() {
        return sslProtocol;
    }
 
    @JsonProperty
    public void setSslProtocol(String sslProtocol) {
        this.sslProtocol = sslProtocol;
    }
 
    @JsonProperty
    public String getTrustStore() {
        return trustStore;
    }
 
    @JsonProperty
    public void setTrustStore(String trustStore) {
        this.trustStore = trustStore;
    }
 
    @JsonProperty
    public String getTrustStorePassword() {
        return trustStorePassword;
    }
 
    @JsonProperty
    public void setTrustStorePassword(String trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
    }
 
    @JsonProperty
    public String getTopic() {
        return topic;
    }
 
    @JsonProperty
    public void setTopic(String topic) {
        this.topic = topic;
    }
 
    String brokerUrl;
    String clientID;
    String userName;
    String password;
    String sslProtocol;
    String trustStore;
    String trustStorePassword;
    String topic;
}

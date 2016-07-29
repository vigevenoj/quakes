package com.sharkbaitextraordinaire.quakes;

public class HueConfiguration {

	private String bridgeAddress;
	private String username;
	private String appName;
	private String deviceName;
	
	public String getAddress() {
		return this.bridgeAddress;
	}
	
	public void setAddress(String address) {
		this.bridgeAddress = address;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getAppName() {
		return this.appName;
	}
	
	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	public String getDeviceName() {
		return this.deviceName;
	}
	
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
}

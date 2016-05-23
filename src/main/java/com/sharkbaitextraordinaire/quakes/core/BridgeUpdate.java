package com.sharkbaitextraordinaire.quakes.core;

import java.util.Map;

public class BridgeUpdate {
	
	public static final String BROADWAY = "broadway";
	public static final String BURNSIDE = "burnside";
	public static final String MORRISON = "morrison";
	public static final String HAWTHORNE = "hawthorne";
	
	Map<String,String> changed;
	Map<String,Map<String,Object>> bridgeUpdates;
	
	public BridgeUpdate(Map<String, Object> json) {
		this.changed = (Map<String, String>) json.get("changed");
		this.bridgeUpdates = (Map<String,Map<String, Object>>) json.remove("changed");
	}
	
	public BridgeUpdate() {}
	
	// changed['bridge']
	// changed['item'] should be status
	// bridge = changed["bridge"]
	
	// bridgeUpdates
	public String getChangedBridge() {
		return changed.get("bridge");
	}
	
	public String getChangedItem() {
		return changed.get("item"); // this is hopefully "status"
	}
	
	public String getChangedBridgeChanges() {
		String bridge = getChangedBridge();
		String item = getChangedItem();
		
		if (item == "status") {
		
			Map<String,Object> updates = getBridgeUpdates().get(bridge);
			
		}
		
		return null;
	}
	
	public Object getBridge(String bridgeName) {
		return bridgeUpdates.get(bridgeName);
	}
	
	public Map<String,String> getChanged() {
		return this.changed;
	}
	
	public void setChanged(Map<String,String> changed) {
		this.changed = changed;
	}
	
	public Map<String,Map<String, Object>> getBridgeUpdates() {
		return this.bridgeUpdates;
	}
	
	public void setBridgeUpdates(Map<String,Map<String, Object>> bridgeUpdates) {
		this.bridgeUpdates = bridgeUpdates;
	}
	
}

package com.sharkbaitextraordinaire.quakes.core;

import java.util.Map;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


@JsonDeserialize(using = BridgeUpdateDeserializer.class)
public class BridgeUpdate {
	
	public static final String BROADWAY = "broadway";
	public static final String BURNSIDE = "burnside";
	public static final String MORRISON = "morrison";
	public static final String HAWTHORNE = "hawthorne";
	
	private Map<String,String> changed;
	private Map<String,SingleBridgeUpdate> bridgeUpdates;
  // internally this is just a Map<String,Object>
  // one object is a Map<String,String>
  // the other objects are Map<String,SingleBridgeUpdate>
	
	public BridgeUpdate(Map<String, Object> json) {
		this.changed = (Map<String, String>) json.get("changed");
		this.bridgeUpdates = (Map<String,SingleBridgeUpdate>) json.remove("changed");
	}
	
	public BridgeUpdate() {}
	
	public String getChangedBridge() {
		return changed.get("bridge");
	}
	
	public String getChangedItem() {
		return changed.get("item"); // this is hopefully "status"
	}
	
	
//	public SingleBridgeUpdate getBridge(String bridgeName) {
//		return bridgeUpdates.get(bridgeName);
//	}
	
	public Map<String,String> getChanged() {
		return this.changed;
	}
	
	public void setChanged(Map<String,String> changed) {
		this.changed = changed;
	}
  
  public Map<String,SingleBridgeUpdate> getBridgeUpdates() {
    return this.bridgeUpdates;
  }

  public void setBridgeUpdates(Map<String,SingleBridgeUpdate> bridgeUpdates) {
    this.bridgeUpdates = bridgeUpdates;
  }
}

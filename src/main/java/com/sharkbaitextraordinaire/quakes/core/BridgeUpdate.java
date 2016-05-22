package com.sharkbaitextraordinaire.quakes.core;

import java.util.Map;

public class BridgeUpdate {
	Map<String,String> changed;
	Map<String,Map<String,Object>> bridgeUpdates;
	
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
	
	
}

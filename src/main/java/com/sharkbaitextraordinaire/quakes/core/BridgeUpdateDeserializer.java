package com.sharkbaitextraordinaire.quakes.core;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Iterator;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.io.IOException;

public class BridgeUpdateDeserializer extends JsonDeserializer<BridgeUpdate> {
  public BridgeUpdate deserialize(JsonParser jp, DeserializationContext context) throws IOException, JsonProcessingException {
    JsonNode node = jp.getCodec().readTree(jp);

    JsonNode changedNode = node.get("changed");
    HashMap<String,String> changedMap = new HashMap<String,String>();
    String changedBridge = changedNode.get("bridge").asText();
    String changedItem = changedNode.get("item").asText();
    changedMap.put("bridge", changedBridge);
    changedMap.put("item", changedItem);

    BridgeUpdate bu = new BridgeUpdate();
    bu.setChanged(changedMap);

    HashMap<String,SingleBridgeUpdate> sbu = new HashMap<String,SingleBridgeUpdate>();

    // iterate over the rest of the nodes or use our existing knowledge about the structure to build the map of SingleBridgeUpdate objects
    Iterator<Map.Entry<String, JsonNode>> nodeIterator = node.fields();
    while (nodeIterator.hasNext()) {
      Map.Entry<String, JsonNode> entry = (Map.Entry<String, JsonNode>) nodeIterator.next();
      if (entry.getKey().equals("changed")) {
        continue;
      } else if (entry.getValue().isObject()) {
        // It is probably a String,SingleBridgeUpdate map
        ObjectNode objnode = (ObjectNode) entry.getValue();
//        sbu.put(entry.getKey(), (SingleBridgeUpdate) objnode.getValue());
      }
    }


    return bu;
  }
}

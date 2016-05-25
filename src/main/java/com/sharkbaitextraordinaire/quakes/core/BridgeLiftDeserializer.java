package com.sharkbaitextraordinaire.quakes.core;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.IntNode;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.io.IOException;

public class BridgeLiftDeserializer extends JsonDeserializer<BridgeLift> {
  private SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss.SSS'Z'");

  public BridgeLift deserialize(JsonParser jp, DeserializationContext context) throws IOException, JsonProcessingException {
    JsonNode node = jp.getCodec().readTree(jp);
    int id = (Integer) ((IntNode) node.get("id")).numberValue();
    int bridgeId = (Integer) ((IntNode) node.get("bridgeId")).numberValue();
    try {
      Date upTime = formatter.parse(node.get("upTime").textValue());
      Date downTime = formatter.parse(node.get("downTime").textValue());
      Date createdAt = formatter.parse(node.get("createdAt").textValue());
      Date updatedAt = formatter.parse(node.get("updatedAt").textValue());

      return new BridgeLift(id, bridgeId, upTime, downTime, createdAt, updatedAt);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }
}

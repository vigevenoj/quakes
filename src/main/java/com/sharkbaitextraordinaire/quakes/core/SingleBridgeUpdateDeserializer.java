package com.sharkbaitextraordinaire.quakes.core;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.io.IOException;

public class SingleBridgeUpdateDeserializer extends JsonDeserializer<SingleBridgeUpdate> {
  public SingleBridgeUpdate deserialize(JsonParser jp, DeserializationContext context) throws IOException, JsonProcessingException {
    JsonNode node = jp.getCodec().readTree(jp);
    return null;
  }
}

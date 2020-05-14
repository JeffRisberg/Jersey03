package com.company.common.services.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Iterator;

public class ObjectUtils {
  private static final ObjectMapper DEFAULT_OBJECT_MAPPER = new ObjectMapper();
  private static final ThreadLocal<JSONParser> DEFAULT_PARSER = ThreadLocal.withInitial(() -> {
    return new JSONParser();
  });

  public ObjectUtils() {
  }

  public static ObjectMapper getDefaultObjectMapper() {
    return DEFAULT_OBJECT_MAPPER;
  }

  public static JSONParser getDefaultJSONParser() {
    return (JSONParser) DEFAULT_PARSER.get();
  }

  public static JSONObject toJSONObject(String json) throws Exception {
    return (JSONObject) getDefaultJSONParser().parse(json);
  }

  public static String toJSONString(JSONObject json, boolean prettyPrint) throws Exception {
    return prettyPrint ? getDefaultObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(json) : json.toJSONString();
  }

  public static JsonNode merge(JsonNode main, JsonNode update) {
    Iterator fieldNames = update.fieldNames();

    while (true) {
      while (fieldNames.hasNext()) {
        String fieldName = (String) fieldNames.next();
        JsonNode jsonNode = main.get(fieldName);
        if (jsonNode != null && jsonNode.isObject()) {
          merge(jsonNode, update.get(fieldName));
        } else if (main instanceof ObjectNode) {
          JsonNode value = update.get(fieldName);
          ((ObjectNode) main).replace(fieldName, value);
        }
      }

      return main;
    }
  }

  public static BufferedImage fromBase64String(String image) throws Exception {
    byte[] bytes = Base64.getDecoder().decode(image);
    ByteArrayInputStream in = new ByteArrayInputStream(bytes);
    return ImageIO.read(in);
  }

  public static String toBase64String(RenderedImage image, String format) throws Exception {
    byte[] bytes = toBase64Bytes(image, format);
    return Base64.getEncoder().encodeToString(bytes);
  }

  public static byte[] toBase64Bytes(RenderedImage image, String format) throws Exception {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ImageIO.write(image, format, out);
    return out.toByteArray();
  }
}

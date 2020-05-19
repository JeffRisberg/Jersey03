package com.company.common.base.sql;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;

@Converter
@Slf4j
public class JpaConverterJson implements AttributeConverter<JSONObject, String> {

  private final static ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String convertToDatabaseColumn(JSONObject meta) {
    try {
      return objectMapper.writeValueAsString(meta);
    } catch (JsonProcessingException ex) {
      log.error("JPAConverter:", ex);
      return null;
    }
  }

  @Override
  public JSONObject convertToEntityAttribute(String dbData) {
    try {
      if (dbData != null && dbData != "") {
        return objectMapper.readValue(dbData, JSONObject.class);
      } else {
        return null;
      }
    } catch (IOException ex) {
      log.error("Unexpected IOEx decoding JSON object from database: " + dbData, ex);
      return null;
    }
  }

}

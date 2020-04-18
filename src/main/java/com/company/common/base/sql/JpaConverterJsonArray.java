package com.company.common.base.sql;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter
@Slf4j
public class JpaConverterJsonArray implements AttributeConverter<JSONArray, String> {

  private final static ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String convertToDatabaseColumn(JSONArray meta) {
    try {
      return objectMapper.writeValueAsString(meta);
    } catch (JsonProcessingException ex) {
      log.error("JPAConverter:", ex);
      return null;
    }
  }

  @Override
  public JSONArray convertToEntityAttribute(String dbData) {
    try {
      if (dbData != null && dbData != "") {
        return objectMapper.readValue(dbData, JSONArray.class);
      } else {
        return null;
      }
    } catch (IOException ex) {
      log.error("Unexpected IOEx decoding JSONArray object from database: " + dbData, ex);
      return null;
    }
  }

}

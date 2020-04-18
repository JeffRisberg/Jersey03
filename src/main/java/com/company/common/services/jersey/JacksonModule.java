package com.company.common.services.jersey;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.inject.AbstractModule;

/**
 * This wires up Jackson for Jersey to use for de/serialization of objects to/from Resources (Controllers).
 */
public final class JacksonModule extends AbstractModule {

  @Override
  protected void configure() {
    ObjectMapper objectMapper = new ObjectMapper();

    objectMapper.registerModule(new JodaModule());
    SerializationConfig config = objectMapper.getSerializationConfig()
      .withoutFeatures(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    objectMapper.setConfig(config);

    bind(ObjectMapper.class).toInstance(objectMapper);
  }
}

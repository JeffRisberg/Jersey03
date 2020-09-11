package com.company.common.services.jersey;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import javax.ws.rs.ext.ContextResolver;

/**
 * This allows returning different ObjectMappers depending upon context.
 */
public final class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {

  private final ObjectMapper objectMapper;

  @Inject
  ObjectMapperContextResolver(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public ObjectMapper getContext(Class<?> type) {
    return objectMapper;
  }
}

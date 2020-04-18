package com.company.jersey03.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

public class RestTools {
  static final ObjectMapper objectMapper = new ObjectMapper();

  public static String getErrorJson(String message, Boolean userError, Optional<Throwable> e) {
    try {
      RequestError error = new RequestError();
      error.setErrorCode(500);
      error.setUserError(userError);
      error.setMessage(message);
      StringBuilder sb = new StringBuilder();
      e.ifPresent(throwable -> sb.append(" : ").append(e.toString()));
      error.setErrorMessage(sb.toString());
      return objectMapper.writeValueAsString(error);
    } catch (Exception ex) {
      return null;
    }
  }

  public static String getErrorJson(String message, Optional<Throwable> e) {
    try {
      RequestError error = new RequestError();
      error.setErrorCode(500);
      StringBuilder sb = new StringBuilder();
      sb.append(message);
      e.ifPresent(throwable -> sb.append(" : ").append(e.toString()));
      error.setErrorMessage(sb.toString());
      return objectMapper.writeValueAsString(error);
    } catch (Exception ex) {
      return null;
    }
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class RequestError {
    private int errorCode;
    private String errorMessage;
    private String message; // Show to the User if userError=true
    private Boolean userError; // Display to the user?
  }
}

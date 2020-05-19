package com.company.jersey03.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.json.simple.JSONObject;

import java.io.Serializable;

/**
 * @author Jeff Risberg
 * @since 10/22/17
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class AbstractDTO implements Serializable {

  private Long id;
  private JSONObject customFieldValues;

}

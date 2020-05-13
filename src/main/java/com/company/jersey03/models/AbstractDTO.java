package com.company.jersey03.models;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Jeff Risberg
 * @since 10/22/17
 */
@Data
public abstract class AbstractDTO implements Serializable {

  protected Long id;
}

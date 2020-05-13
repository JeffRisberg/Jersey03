package com.company.jersey03.models;

import lombok.Data;

import java.util.Date;

/**
 * @author Jeff Risberg
 * @since 10/22/17
 */
@Data
public abstract class AbstractDatedDTO extends AbstractDTO {

  protected Date dateCreated;
  protected Date lastUpdated;
}

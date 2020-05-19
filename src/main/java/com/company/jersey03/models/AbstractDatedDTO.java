package com.company.jersey03.models;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Jeff Risberg
 * @since 10/22/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractDatedDTO extends AbstractDTO {

  protected Date dateCreated;
  protected Date lastUpdated;
}

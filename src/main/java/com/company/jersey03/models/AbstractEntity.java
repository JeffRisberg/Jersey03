package com.company.jersey03.models;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author Jeff Risberg
 * @since 10/22/17
 */
@Data
@MappedSuperclass
public class AbstractEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected Long id;
}

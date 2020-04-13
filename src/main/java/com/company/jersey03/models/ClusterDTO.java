package com.company.jersey03.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClusterDTO {

  private Long id;

  private String clusterName;
  private String attributes;
  private Date createdDate;
  private Long version;
  private String usageType;

  private String clusterDescription;

  private String criteria;

  private String type;

  private Long parentClusterId;
}

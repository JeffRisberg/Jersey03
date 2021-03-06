package com.company.jersey03.models;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ClusterDTO extends AbstractDatedDTO {

  private String clusterName;
  private String attributes;
  private Long version;
  private String usageType;

  private String clusterDescription;

  private String criteria;

  private String type;

  private Long parentClusterId;
}

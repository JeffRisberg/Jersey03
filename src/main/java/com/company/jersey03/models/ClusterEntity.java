package com.company.jersey03.models;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = "clusters")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ClusterEntity extends AbstractEntity {

  @Column(length = 2048)
  private String clusterName;
  @Column(length = 4096)
  private String attributes;
  @Column(name = "created_date")
  private Date createdDate;
  @Column(name = "version")
  private Long version;
  @Column(name = "usage_type")
  private String usageType;

  @Column(name = "cluster_description")
  private String clusterDescription;

  @Column(name = "criteria")
  private String criteria;

  @Column(name = "type")
  private String type;

  @OneToMany(mappedBy = "cluster", cascade = CascadeType.ALL)
  private List<ClusterEntityMapping> clusterEntityMappings;

  @Column(name = "parent_cluster_id")
  private Long parentClusterId;

  @Column(name = "disabled_by")
  private Long disabledBy;

  @Column(name = "created_by")
  private Long createdBy;

  @Column(name = "disable_reason")
  private String disabledByReason;

  @Column(name = "disabled")
  private Boolean disabled;

  @Column(name = "cloned")
  private Boolean cloned;

  public ClusterEntity merge(ClusterEntity entity) {
    if (!entity.usageType.equalsIgnoreCase("undefined")) {
      this.usageType = entity.usageType;
    }

    if (StringUtils.isNotEmpty(entity.getAttributes())) {
      this.setAttributes(entity.getAttributes());
    }

    if (StringUtils.isNotEmpty(entity.getClusterDescription())) {
      this.setClusterDescription(entity.getClusterDescription());
    }

    if (StringUtils.isNotEmpty(entity.getCriteria())) {
      this.setCriteria(entity.getCriteria());
    }

    if (!entity.type.equalsIgnoreCase("undefined")) {
      this.type = entity.type;
    }

    // merge the mappings
    if (CollectionUtils.isNotEmpty(entity.getClusterEntityMappings())) {
      this.getClusterEntityMappings().addAll(entity.getClusterEntityMappings());
    }
    return this;
  }

  public ClusterDTO toDTO() {
    ClusterDTO result = new ClusterDTO();
    update(result);

    result.setClusterName(getClusterName());
    result.setClusterDescription(getClusterDescription());
    result.setAttributes(getAttributes());
    result.setType(getType());
    result.setUsageType(getUsageType());
    result.setVersion(getVersion());
    result.setCriteria(getCriteria());
    result.setParentClusterId(getParentClusterId());

    return result;
  }
}

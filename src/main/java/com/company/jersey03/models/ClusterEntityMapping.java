package com.company.jersey03.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "cluster_entity_mappings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClusterEntityMapping {

  @Id
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "cluster_id", nullable = false)
  private ClusterEntity cluster;

  @Column(name = "entity_id")
  private Long entityId;

  @Column(name = "entity_type")
  private String entityType;
}

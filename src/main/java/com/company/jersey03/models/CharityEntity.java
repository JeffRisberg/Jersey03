package com.company.jersey03.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "charities")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CharityEntity extends AbstractDatedEntity {

  @Column(name = "name", nullable = false)
  protected String name;

  @Column(name = "ein", nullable = false)
  private String ein;

  @Column(name = "description", nullable = true)
  protected String description;

  @Column(name = "website", nullable = true)
  protected String website;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "entity_id")
  @Where(clause = "entity_type = 'Charity'")
  List<CustomFieldValueEntity> customFields = new ArrayList();

  public CharityDTO toDTO() {
    CharityDTO result = new CharityDTO();
    update(result);

    result.setName(getName());
    result.setEin(getEin());
    result.setDescription(getDescription());
    result.setWebsite(getWebsite());

    if (customFields.size() > 0) {
      JSONObject customFieldValues = new JSONObject();
      for (CustomFieldValueEntity cfve : customFields) {
        customFieldValues.put(cfve.getField().getFieldName(), cfve.getFieldValue());
      }
      result.setCustomFieldValues(customFieldValues);
    }

    return result;
  }
}

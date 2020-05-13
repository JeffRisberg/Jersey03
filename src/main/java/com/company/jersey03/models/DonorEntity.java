package com.company.jersey03.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jeff Risberg
 * @since 11/3/17
 */

@Entity
@Table(name = "donors")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class DonorEntity extends AbstractDatedEntity {

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "age", nullable = true)
  private Integer age;

  @OneToMany(mappedBy = "donor")
  @JsonIgnore
  private List<DonationEntity> donations = new ArrayList<DonationEntity>();

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "entity_id")
  @Where(clause = "entity_type = 'Donor'")
  List<CustomFieldValueEntity> customFields = new ArrayList();

  public DonorDTO toDTO() {
    DonorDTO result = new DonorDTO();
    update(result);

    result.setId(getId());
    result.setFirstName(getFirstName());
    result.setLastName(getLastName());
    result.setAge(age);

    JSONObject customFieldValues = new JSONObject();
    for (CustomFieldValueEntity cfve : this.customFields) {
      customFieldValues.put(cfve.getField().getFieldName(), cfve.getFieldValue());
    }
    result.setCustomFieldValues(customFieldValues);

    return result;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("DonorEntity[firstName=" + firstName);
    sb.append(", lastName=" + lastName);
    sb.append(", age=" + age);
    sb.append("]");

    return sb.toString();
  }
}

package com.company.jersey03.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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

  //public Optional<CharityEntity> create(CharityEntity charity, Session session) {
  //  CharityEntity charityEntity = new CharityEntity();

  //  return Optional.of(charityEntity);
  //}

  public CharityDTO toDTO() {
    CharityDTO result = new CharityDTO();
    update(result);

    result.setId(getId());
    result.setDateCreated(getDateCreated());
    result.setLastUpdated(getLastUpdated());
    result.setName(getName());
    result.setEin(getEin());
    result.setDescription(getDescription());
    result.setWebsite(getWebsite());

    return result;
  }

  public JSONObject toJSON() {
    JSONObject result = new JSONObject();

    result.put("id", getId());
    result.put("dateCreated", getDateCreated());
    result.put("lastUpdated", getLastUpdated());
    result.put("name", getName());
    result.put("ein", getEin());
    if (getDescription() != null) {
      result.put("description", getDescription());
    }
    if (getWebsite() != null) {
      result.put("website", getWebsite());
    }

    return result;
  }
}

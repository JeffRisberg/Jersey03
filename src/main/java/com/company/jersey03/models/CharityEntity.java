package com.company.jersey03.models;

import com.company.jersey03.services.FieldService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CharityEntity extends AbstractDatedEntity {

  @Column(name = "name", nullable = false)
  protected String name;

  @Column(name = "ein", nullable = false)
  private String ein;

  @Column(name = "description", nullable = true)
  protected String description;

  @Column(name = "website", nullable = true)
  protected String website;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "entity_id")
  @Where(clause = "entity_type = 'Charity'")
  List<CustomFieldValueEntity> customFieldValues = new ArrayList();

  public CharityDTO toDTO() {
    CharityDTO result = new CharityDTO();
    update(result);

    result.setName(getName());
    result.setEin(getEin());
    result.setDescription(getDescription());
    result.setWebsite(getWebsite());

    if (customFieldValues.size() > 0) {
      JSONObject customFieldValuesJSON = new JSONObject();
      for (CustomFieldValueEntity cfve : customFieldValues) {
        customFieldValuesJSON.put(cfve.getField().getFieldName(), cfve.getFieldValue());
      }
      result.setCustomFieldValues(customFieldValuesJSON);
    }

    return result;
  }

  public CharityEntity applyDTO(CharityDTO dto, FieldService fieldService) {
    String entityType = "Charity";

    if (dto != null) {
      super.applyDTO(dto);

      if (dto.getName() != null) this.setName(dto.getName());
      if (dto.getEin() != null) this.setEin(dto.getEin());
      if (dto.getDescription() != null) this.setDescription(dto.getDescription());
      if (dto.getWebsite() != null) this.setWebsite(dto.getWebsite());

      if (dto.getCustomFieldValues() != null) {
        List<CustomFieldValueEntity> addList = new ArrayList<>();

        if (this.customFieldValues.size() > 0) {
          for (CustomFieldValueEntity cfve : this.customFieldValues) {
            String fieldName = cfve.getField().getFieldName();

            if (dto.getCustomFieldValues().containsKey(fieldName)) {
              // this cfve is being updated
              String fieldValue = (String) dto.getCustomFieldValues().get(fieldName);

              cfve.setFieldValue(fieldValue);
              dto.getCustomFieldValues().remove(fieldName);
            }
          }
        }

        // Now find the new fieldValues
        for (Object fieldName : dto.getCustomFieldValues().keySet()) {
          if (((String) fieldName).startsWith("-"))
            continue;

          Field field = fieldService.getByContentTypeFieldName(entityType, (String) fieldName);

          if (field != null) {
            // a cfve must be created
            String fieldValue = (String) dto.getCustomFieldValues().get(fieldName);

            CustomFieldValueEntity newCfve =
              new CustomFieldValueEntity(field, field.getId(), entityType, this.getId(), fieldValue);
            addList.add(newCfve);
          } else {
            log.error("tried to add unknown field " + fieldName);
          }
        }

        this.customFieldValues.addAll(addList);
      }
    }
    return this;
  }

  public List<Long> findDeletes(CharityDTO dto) {
    List<Long> delList = new ArrayList<>();

    if (dto != null) {
      if (dto.getCustomFieldValues() != null) {
        if (this.customFieldValues.size() > 0) {
          for (CustomFieldValueEntity cfve : this.customFieldValues) {
            String fieldName = cfve.getField().getFieldName();

            if (dto.getCustomFieldValues().containsKey("-" + fieldName)) {
              // this cfve is being deleted
              fieldName = fieldName.substring(1);

              delList.add(cfve.getId());
              dto.getCustomFieldValues().remove("-" + fieldName);
            }
          }
        }
      }
    }
    return delList;
  }
}

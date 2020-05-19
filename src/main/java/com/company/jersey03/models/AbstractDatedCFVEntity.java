package com.company.jersey03.models;

import com.company.jersey03.services.FieldService;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.*;
import org.json.simple.JSONObject;

/**
 * @author Jeff Risberg
 * @since 10/22/17
 */
@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
@Slf4j
@FilterDef(name = "entityTypeFilter",
    parameters = @ParamDef(name = "entityType", type = "string")
)
public abstract class AbstractDatedCFVEntity extends AbstractDatedEntity {

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "entity_id")
  @Filter(
      name = "entityTypeFilter",
      condition = "entity_type = :entityType"
  )
  List<CustomFieldValue> customFieldValues = new ArrayList();

  protected boolean update(AbstractDatedDTO dto) {
    if (dto != null) {
      super.update(dto);

      if (customFieldValues.size() > 0) {
        JSONObject customFieldValuesJSON = new JSONObject();
        for (CustomFieldValue cfv : customFieldValues) {
          customFieldValuesJSON.put(cfv.getField().getFieldName(), cfv.getFieldValue());
        }
        dto.setCustomFieldValues(customFieldValuesJSON);
      }
      return true;
    }
    return false;
  }

  public List<Long> findCustomFieldValueDeletes(AbstractDTO dto) {
    List<Long> delList = new ArrayList<>();

    if (dto != null) {
      if (dto.getCustomFieldValues() != null) {
        if (this.customFieldValues.size() > 0) {
          for (CustomFieldValue cfv : this.customFieldValues) {
            String fieldName = cfv.getField().getFieldName();

            if (dto.getCustomFieldValues().containsKey("-" + fieldName)) {
              // this cfv is being deleted
              fieldName = fieldName.substring(1);

              delList.add(cfv.getId());
              dto.getCustomFieldValues().remove("-" + fieldName);
            }
          }
        }
      }
    }
    return delList;
  }

  public AbstractDatedCFVEntity applyDTO(AbstractDatedDTO dto, String entityType,
      FieldService fieldService) {
    if (dto != null) {
      super.applyDTO(dto);

      if (dto.getCustomFieldValues() != null) {
        List<CustomFieldValue> addList = new ArrayList<>();

        if (this.customFieldValues.size() > 0) {
          for (CustomFieldValue cfv : this.customFieldValues) {
            String fieldName = cfv.getField().getFieldName();

            if (dto.getCustomFieldValues().containsKey(fieldName)) {
              // this cfv is being updated
              String fieldValue = (String) dto.getCustomFieldValues().get(fieldName);

              cfv.setFieldValue(fieldValue);
              dto.getCustomFieldValues().remove(fieldName);
            }
          }
        }

        // Now find the new fieldValues
        for (Object fieldName : dto.getCustomFieldValues().keySet()) {
          if (((String) fieldName).startsWith("-")) {
            continue;
          }

          Field field = fieldService.getByContentTypeFieldName(entityType, (String) fieldName);

          if (field != null) {
            // a cfv must be created
            String fieldValue = (String) dto.getCustomFieldValues().get(fieldName);

            CustomFieldValue newCfv =
                new CustomFieldValue(field, field.getId(), entityType, this.getId(), fieldValue);
            addList.add(newCfv);
          } else {
            log.error("tried to add unknown field " + fieldName);
          }
        }

        this.customFieldValues.addAll(addList);
      }
    }
    return this;
  }
}

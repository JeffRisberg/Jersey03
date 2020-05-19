package com.company.jersey03.endpoints;

import com.company.common.FilterDescription;
import com.company.common.FilterOperator;
import com.company.common.SortDescription;
import com.company.common.SortDirection;
import com.company.common.services.util.ObjectUtils;
import com.company.jersey03.services.CustomFieldValueService;
import com.company.jersey03.services.FieldService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Jeff Risberg
 * @since 11/02/17
 */
public class AbstractEndpoint {

  protected FieldService fieldService;
  protected CustomFieldValueService customFieldValueService;

  protected static final ObjectMapper objectMapper = ObjectUtils.getDefaultObjectMapper();

  @Inject
  public AbstractEndpoint(FieldService fieldService,
                          CustomFieldValueService customFieldValueService) {
    this.fieldService = fieldService;
    this.customFieldValueService = customFieldValueService;
  }

  /**
   * Generate a sorting specification from the query params of the request, of the
   * form "sort=field1,-field2".
   *
   * @param queryParams
   * @return list of Sort Descriptors
   */
  protected List<SortDescription> parseSorting(MultivaluedMap<String, String> queryParams) {
    List<SortDescription> sortDescs = new ArrayList<SortDescription>();

    for (Map.Entry<String, List<String>> entrySet : queryParams.entrySet()) {
      String fieldName = entrySet.getKey();
      if ("sort".equals(fieldName)) {
        List<String> fieldValues = entrySet.getValue();

        for (String fieldValue : fieldValues) {
          SortDirection sortDir = SortDirection.asc;

          if (fieldValue.startsWith("-")) {
            fieldValue = fieldValue.substring(1);
            sortDir = SortDirection.desc;
          }

          sortDescs.add(new SortDescription(fieldValue, sortDir));
        }
      }
    }

    return sortDescs;
  }

  /**
   * Generate a filtering specification from the query params of the request, of the
   * form "fieldname=fieldValue".
   *
   * @param queryParams
   * @return list of Filter Descriptors
   */
  protected List<FilterDescription> parseFiltering(MultivaluedMap<String, String> queryParams) {
    List<FilterDescription> filterDescs = new ArrayList<FilterDescription>();

    for (Map.Entry<String, List<String>> entrySet : queryParams.entrySet()) {
      String fieldName = entrySet.getKey();
      if ("limit".equals(fieldName) || "offset".equals(fieldName) || "sort".equals(fieldName))
        continue;

      List<String> fieldValues = entrySet.getValue();

      if (fieldValues.size() > 0) {
        filterDescs.add(new FilterDescription(fieldName, FilterOperator.eq, fieldValues.get(0)));
      }
    }

    return filterDescs;
  }

  /**
   * Generate the response for a fetch of a single entity.
   *
   * @param data if null, triggers 404 status code.
   * @return Response
   */
  protected Response createEntityResponse(Object data, List<Error> errors) {
    List<Error> resultErrors = new ArrayList<Error>();

    Envelope envelope = new Envelope(data, resultErrors);

    if (errors != null) {
      for (Error error : errors) {
        resultErrors.add(error);
      }
    }

    if (data == null) {
      resultErrors.add(new Error("Not found"));

      return Response.status(Response.Status.NOT_FOUND).entity(envelope).build();
    } else {
      return Response.status(Response.Status.OK).entity(envelope).build();
    }
  }

  /**
   * Generate the response for a fetch of a collection of entities.
   *
   * @param data
   * @param totalCount
   * @param limit
   * @param offset
   * @return Response
   */
  protected Response createEntityListResponse(
    List data,
    long totalCount,
    int limit,
    int offset,
    List<Error> errors) {
    if (data == null) {
      throw new IllegalArgumentException("missing data");
    }

    List<Error> resultErrors = new ArrayList<Error>();
    Envelope envelope = new Envelope(data, totalCount, limit, offset, errors);

    if (errors != null) {
      for (Error error : errors) {
        resultErrors.add(error);
      }
    }

    return Response.status(Response.Status.OK).entity(envelope).build();
  }

  /**
   * Generate the response for a delete.
   *
   * @param errors
   * @return
   */
  protected Response createDeleteResponse(Object data, List<Error> errors)
    throws WebApplicationException {
    List<Error> resultErrors = new ArrayList<Error>();
    Envelope envelope = new Envelope(null, errors);

    if (errors != null) {
      for (Error error : errors) {
        resultErrors.add(error);
      }
    }

    if (data == null) {
      resultErrors.add(new Error("Not found"));

      throw new WebApplicationException(
        Response.status(Response.Status.NOT_FOUND).entity(envelope).build());
    } else {
      return Response.status(Response.Status.OK).entity(envelope).build();
    }
  }
}

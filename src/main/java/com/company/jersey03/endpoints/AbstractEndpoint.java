package com.company.jersey03.endpoints;

import com.company.jersey03.common.FilterDescription;
import com.company.jersey03.common.FilterOperator;
import com.company.jersey03.common.SortDescription;
import com.company.jersey03.common.SortDirection;

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

  /**
   * Generate a sorting specification from the given sort string, such as "-field1,field2".
   *
   * @param sortStr
   * @return list of Sort Descriptors
   */
  protected List<SortDescription> parseSortStr(String sortStr) {
    List<SortDescription> sortDescs = new ArrayList<SortDescription>();

    String[] fragments = sortStr.split(",");

    for (int i = 0; i < fragments.length; i++) {
      String fieldName = fragments[i];
      SortDirection sortDir = SortDirection.asc;

      if (fieldName.startsWith("-")) {
        fieldName = fieldName.substring(1);
        sortDir = SortDirection.desc;
      }

      //sortDescs.add(new SortDescription(new FieldDesc(fieldName), sortDir));
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
  protected Response createEntityResponse(Object data, List<com.company.jersey03.endpoints.Error> errors) {
    List<com.company.jersey03.endpoints.Error> resultErrors = new ArrayList<com.company.jersey03.endpoints.Error>();

    Envelope envelope = new Envelope(data, resultErrors);

    if (errors != null) {
      for (com.company.jersey03.endpoints.Error error : errors) {
        resultErrors.add(error);
      }
    }

    if (data == null) {
      resultErrors.add(new com.company.jersey03.endpoints.Error("Not found"));

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
    List<com.company.jersey03.endpoints.Error> errors) {
    if (data == null) {
      throw new IllegalArgumentException("missing data");
    }

    List<com.company.jersey03.endpoints.Error> resultErrors = new ArrayList<com.company.jersey03.endpoints.Error>();
    Envelope envelope = new Envelope(data, totalCount, limit, offset, errors);

    if (errors != null) {
      for (com.company.jersey03.endpoints.Error error : errors) {
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
  protected Response createDeleteResponse(Object data, List<com.company.jersey03.endpoints.Error> errors)
    throws WebApplicationException {
    List<com.company.jersey03.endpoints.Error> resultErrors = new ArrayList<com.company.jersey03.endpoints.Error>();
    Envelope envelope = new Envelope(null, errors);

    if (errors != null) {
      for (com.company.jersey03.endpoints.Error error : errors) {
        resultErrors.add(error);
      }
    }

    if (data == null) {
      resultErrors.add(new com.company.jersey03.endpoints.Error("Not found"));

      throw new WebApplicationException(
        Response.status(Response.Status.NOT_FOUND).entity(envelope).build());
    } else {
      return Response.status(Response.Status.OK).entity(envelope).build();
    }
  }
}

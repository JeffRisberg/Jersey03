package com.company.jersey03.endpoints;

import com.company.common.FilterDescription;
import com.company.common.SortDescription;
import com.company.jersey03.models.Field;
import com.company.jersey03.models.FieldDTO;
import com.company.jersey03.services.CustomFieldValueService;
import com.company.jersey03.services.FieldService;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.swagger.annotations.*;
import java.sql.Timestamp;
import java.util.*;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import lombok.extern.slf4j.Slf4j;

@Path("fields")
@Api(value = "Fields", description = "Endpoint for managing Fields")
@Slf4j
public class Fields extends AbstractEndpoint {

  @Inject
  public Fields(FieldService fieldService, CustomFieldValueService customFieldValueService) {
    super(fieldService, customFieldValueService);
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Create a new Field. Set id=0", response = FieldDTO.class)
  public Response createField(FieldDTO field) {
    try {
      field.setDateCreated(new Timestamp(System.currentTimeMillis()));

      Field createdField = fieldService.create(
          new Field().applyDTO(field));

      if (createdField == null) {
        log.error("Cannot create field from {}", createdField);
        return Response.serverError().build();
      }

      return Response.ok(createdField.toDTO()).build();
    } catch (Exception e) {
      log.error("Exception during request", e);
      return Response.serverError()
          .entity(RestTools.getErrorJson("Exception during request", false, Optional.of(e)))
          .build();
    }
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Get all Fields", response = FieldDTO.class, responseContainer = "List")
  public Response getFields(@DefaultValue("50") @QueryParam("limit") int limit,
      @DefaultValue("0") @QueryParam("offset") int offset,
      @Context UriInfo uriInfo) {
    try {
      MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
      List<FilterDescription> filterDescs = this.parseFiltering(queryParams);
      List<SortDescription> sortDescs = this.parseSorting(queryParams);

      List<Field> fields = fieldService.getByCriteria(filterDescs, sortDescs, limit, offset);
      return RestTools.createResponse(fields);
    } catch (Exception e) {
      log.error("Exception during request", e);
      return Response.serverError()
          .entity(RestTools.getErrorJson("Exception during request", false, Optional.of(e)))
          .build();
    }
  }

  @Path("/{fieldId}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Get Field by Id", response = FieldDTO.class)
  public Response getField(@PathParam("fieldId") Long fieldId) {
    try {
      List<Field> fields = new ArrayList<>();
      Field data = fieldService.getById(fieldId);
      if (data != null) {
        fields.add(data);
      }
      return RestTools.createResponse(fields);
    } catch (Exception e) {
      log.error("Exception during request", e);
      return Response.serverError()
          .entity(RestTools.getErrorJson("Exception during request", false, Optional.of(e)))
          .build();
    }
  }

  /*
  @Path("/names/{fieldName}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Get Fields by Name", response = FieldDTO.class, responseContainer = "List")
  public Response getNamedFields(@PathParam("fieldName") String fieldName) {
    try {
      List<Field> fields = fieldService.getForName(fieldName);
      return RestTools.createResponse(fields);
    } catch (Exception e) {
      log.error("Exception during request", e);
      return Response.serverError().entity(RestTools.getErrorJson("Exception during request", false, Optional.of(e))).build();
    }
  }
  */

  /*
  @Path("/content-type-names/{contentTypeName}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Get Fields by ContentType", response = FieldDTO.class, responseContainer = "List")
  public Response getContentTypeFields(@PathParam("contentTypeName") String contentTypeName) {
    try {
      List<Field> fields = fieldService.getForContentTypeName(contentTypeName);
      return RestTools.createResponse(fields);
    } catch (Exception e) {
      log.error("Exception during request", e);
      return Response.serverError().entity(RestTools.getErrorJson("Exception during request", false, Optional.of(e))).build();
    }
  }
  */

  /*
  @Path("/content-type-names/{contentTypeName}/names/{name}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Get Field By ContentType and Name", response = FieldDTO.class, responseContainer = "List")
  public Response getContentTypeAndNameFields(@PathParam("contentTypeName") String contentTypeName, @PathParam("name") String fieldName) {
    try {
      List<Field> fields = fieldService.getForContentTypeNameAndFieldName(contentTypeName, fieldName);
      return RestTools.createResponse(fields);
    } catch (Exception e) {
      log.error("Exception during request", e);
      return Response.serverError().entity(RestTools.getErrorJson("Exception during request", false, Optional.of(e))).build();
    }
  }
  */

  @Path("/{fieldId}")
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Updates a Field identified by id")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "field", value = "Field to update", required = true, dataType = "com.company.dataServer.models.FieldDTO", paramType = "body"),
  })
  public Response updateField(@PathParam("fieldId") long fieldId,
      @ApiParam(hidden = true) String requestBody) {
    try {
      Field field = fieldService.getById(fieldId);

      if (field == null) {
        return Response.serverError()
            .entity(RestTools.getErrorJson("fieldId does not exist in DB", false, Optional.empty()))
            .build();
      }

      FieldDTO fieldDTO;
      try {
        fieldDTO = objectMapper.readValue(requestBody, FieldDTO.class);
        fieldDTO.setId(fieldId);
      } catch (JsonMappingException jme) {
        log.error("Invalid JSON, defaulting to \"{}\" ", jme);
        fieldDTO = new FieldDTO();
      }

      field.applyDTO(fieldDTO);
      field.setLastUpdated(new Timestamp(System.currentTimeMillis()));

      if (fieldService.update(field)) {
        return Response.ok().build();
      } else {
        return Response.serverError().build();
      }
    } catch (Exception e) {
      log.error("Exception during request", e);
      return Response.serverError()
          .entity(RestTools.getErrorJson("Exception during request", false, Optional.of(e)))
          .build();
    }
  }

  @Path("/{fieldId}")
  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Remove a Field by fieldId")
  public Response removeField(@PathParam("fieldId") Long fieldId) {
    if (fieldId == null) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
    try {
      Field data = fieldService.getById(fieldId);
      if (data == null) {
        return Response.serverError()
            .entity(RestTools.getErrorJson("Field does not exist in DB", false, Optional.empty()))
            .build();
      }
      if (fieldService.delete(fieldId)) {
        return Response.ok().build();
      } else {
        return Response.serverError()
            .entity(RestTools.getErrorJson("Unable to unregister Field", false, Optional.empty()))
            .build();
      }
    } catch (Exception e) {
      log.error("Exception during request", e);
      return Response.serverError()
          .entity(RestTools.getErrorJson("Exception during request", false, Optional.of(e)))
          .build();
    }
  }
}

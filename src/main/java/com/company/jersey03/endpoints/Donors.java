package com.company.jersey03.endpoints;

import com.company.common.FilterDescription;
import com.company.common.SortDescription;
import com.company.common.services.util.ObjectUtils;
import com.company.jersey03.models.DonorDTO;
import com.company.jersey03.models.DonorEntity;
import com.company.jersey03.services.DonorService;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Api(value = "Donors", description = "Donor Management")
@Path("donors")
@Slf4j
public class Donors extends AbstractEndpoint {

  protected DonorService donorService;

  private static final ObjectMapper objectMapper = ObjectUtils.getDefaultObjectMapper();
  private static final JSONParser jsonParser = ObjectUtils.getDefaultJSONParser();

  @Inject
  public Donors(DonorService donorService) {
    this.donorService = donorService;
  }

  @POST
  @ApiOperation(value = "Register a new Donor. Set id=0", response = DonorDTO.class)
  @Produces(MediaType.APPLICATION_JSON)
  public Response create(DonorDTO donor) {
    try {
      donor.setDateCreated(new Timestamp(System.currentTimeMillis()));
      donor.setLastUpdated(new Timestamp(System.currentTimeMillis()));

      DonorEntity createdDonor = donorService.create(DonorEntity.toEntity(donor));

      if (createdDonor == null) {
        log.error("Cannot create donor from {}", donor);
        return Response.serverError().build();
      }

      return Response.ok(createdDonor.toDTO()).build();
    } catch (Exception e) {
      log.error("Exception during request", e);
      return Response.serverError().entity(RestTools.getErrorJson("Exception during request", false, Optional.of(e))).build();
    }
  }

  @GET
  @ApiOperation(value = "Gets all Donors", response = DonorDTO.class, responseContainer = "List")
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response fetch(@PathParam("id") Long id) {
    try {
      List<DonorDTO> donors = new ArrayList<>();
      DonorEntity data = donorService.getById(id);
      if (data != null) {
        donors.add(data.toDTO());
      }
      return Response.ok(donors).build();
    } catch (Throwable e) {
      log.error("Exception during request", e);
      return Response.serverError().entity(RestTools.getErrorJson("Exception during request", false, Optional.of(e))).build();
    }
  }

  @GET
  @ApiOperation(value = "Gets all Donors", response = DonorDTO.class, responseContainer = "List")
  @Produces(MediaType.APPLICATION_JSON)
  public Response fetchList(
    @DefaultValue("50") @QueryParam("limit") int limit,
    @DefaultValue("0") @QueryParam("offset") int offset,
    @Context UriInfo uriInfo) {

    try {
      MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
      List<FilterDescription> filterDescs = this.parseFiltering(queryParams);
      List<SortDescription> sortDescs = this.parseSorting(queryParams);

      JSONArray result = new JSONArray();
      List<DonorEntity> donors =
        donorService.getByCriteria(filterDescs, sortDescs, limit, offset);

      for (DonorEntity donor : donors) {
        result.add(donor.toDTO());
      }

      return Response.ok(result).build();
    } catch (Exception e) {
      return null;
    }
  }

  @PUT
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Updates a Donor identified by id")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "donor", value = "Donor to update", required = true, dataType = "com.company.jersey03.models.DonorDTO", paramType = "body"),
  })
  public Response update(@PathParam("id") Long donorId, @ApiParam(hidden = true) String requestBody) {
    log.info("updating donor with id {}", donorId);

    try {
      JsonNode jsonNode;
      try {
        jsonNode = objectMapper.readValue(requestBody, JsonNode.class);
      } catch (JsonMappingException jme) {
        log.error("Invalid JSON, defaulting to \"{}\" ", jme);
        jsonNode = objectMapper.readValue("{}", JsonNode.class);
      }
      DonorEntity priorDonor = donorService.getById(donorId);

      if (priorDonor == null) {
        return Response.serverError().entity(RestTools.getErrorJson("donorId does not exist in DB", false, Optional.empty())).build();
      }

      JsonNode priorNode = objectMapper.valueToTree(priorDonor.toDTO());
      JsonNode updatedNode = ObjectUtils.merge(priorNode, jsonNode);
      DonorDTO updatedDonor = objectMapper.treeToValue(updatedNode, DonorDTO.class);

      updatedDonor.setLastUpdated(new Timestamp(System.currentTimeMillis()));

      DonorEntity updatedDonorEntity = DonorEntity.toEntity(updatedDonor);

      if (donorService.update(updatedDonorEntity)) {
        return Response.ok().build();
      } else {
        return Response.serverError().build();
      }
    } catch (Exception e) {
      log.error("Exception during request", e);
      return Response.serverError().entity(RestTools.getErrorJson("Exception during request", false, Optional.of(e))).build();
    }
  }

  @DELETE
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response delete(@PathParam("id") Long id) {

    try {
      DonorEntity data = donorService.getById(id);

      return createDeleteResponse(data, null);
    } catch (Exception e) {
      return null;
    }
  }
}

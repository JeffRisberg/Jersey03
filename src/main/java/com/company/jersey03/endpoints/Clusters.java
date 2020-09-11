package com.company.jersey03.endpoints;

import com.company.common.FilterDescription;
import com.company.common.SortDescription;
import com.company.jersey03.models.ClusterDTO;
import com.company.jersey03.models.ClusterEntity;
import com.company.jersey03.services.*;
import java.util.*;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import lombok.extern.slf4j.Slf4j;

@Path("clusters")
@Slf4j
public class Clusters extends AbstractEndpoint {

  protected ClusterService clusterService;

  @Inject
  public Clusters(FieldService fieldService, CustomFieldValueService customFieldValueService,
      ClusterService clusterService) {
    super(fieldService, customFieldValueService);
    this.clusterService = clusterService;
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response fetch(@PathParam("id") Long id) {
    try {
      List<ClusterDTO> clusters = new ArrayList<>();
      ClusterEntity data = clusterService.getById(id);
      if (data != null) {
        clusters.add(data.toDTO());
      }
      return Response.ok(clusters).build();
    } catch (Throwable e) {
      log.error("Exception during request", e);
      return Response.serverError()
          .entity(RestTools.getErrorJson("Exception during request", false, Optional.of(e)))
          .build();
    }
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response fetchList(
      @DefaultValue("50") @QueryParam("limit") int limit,
      @DefaultValue("0") @QueryParam("offset") int offset,
      @Context UriInfo uriInfo) {

    try {
      MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
      List<FilterDescription> filterDescs = this.parseFiltering(queryParams);
      List<SortDescription> sortDescs = this.parseSorting(queryParams);

      List<ClusterDTO> result = new ArrayList<>();
      List<ClusterEntity> clusters =
          clusterService.getByCriteria(filterDescs, sortDescs, limit, offset);

      for (ClusterEntity cluster : clusters) {
        result.add(cluster.toDTO());
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
  public Response update(@PathParam("id") Long id) {
    return Response.ok(null).build();
  }

  @DELETE
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response delete(@PathParam("id") Long id) {
    try {
      ClusterEntity data = clusterService.getById(id);

      return createDeleteResponse(data, null);
    } catch (Exception e) {
      return null;
    }
  }
}

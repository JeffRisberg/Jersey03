package com.company.jersey03.endpoints;

import io.swagger.annotations.*;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

@Api(value = "Hello", description = "Simple example endpoint")
@Singleton
@Path("hello")
@Slf4j
public class Hello {

  private static final String AUTHZ_HEADER = "Authorization";
  private static final String APP_TOKEN_HEADER = "x-app-token";

  @Inject
  public Hello() {
  }

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @ApiOperation(value = "Say hello", response = String.class)
  public Response handle() {
    String results = "Hello There";
    return Response.status(Response.Status.OK).entity(results).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/incidents")
  @ApiOperation(value = "Creates Incidents")
  public Response createIncidents(
      @HeaderParam(AUTHZ_HEADER) String authHeader,
      @HeaderParam(APP_TOKEN_HEADER) String appTokenHeader,
      @ApiParam() String body) {
    log.info("Received Incident");

    String results = "Hello There";
    return Response.status(Response.Status.OK).entity(results).build();
  }
}

package com.company.jersey03.endpoints;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Jeff Risberg
 * @since 11/30/17
 */
@Api(value = "OAuth2", description = "Form-based endpoint")
@Singleton
@Path("/tenants/{tenantId}")
public class OAuth2Endpoint {

  @Inject
  public OAuth2Endpoint() {
  }

  @POST
  @Path("/test1")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Test1", response = OAuth2Request.class)
  public Response test1(@PathParam("tenantId") String tenantId,
                        @FormParam("alpha") String alpha,
                        @FormParam("beta") String beta) {
    OAuth2Request result = new OAuth2Request();
    result.tenantId = tenantId;
    result.alpha = alpha;
    result.beta = beta;

    return Response.status(Response.Status.OK).entity(result).build();
  }

  @POST
  @Path("/test2")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Test2", response = OAuth2Request.class)
  public Response test2(@PathParam("tenantId") String tenantId,
                        @FormParam("alpha") String alpha,
                        @FormParam("beta") String beta) {
    OAuth2Request result = new OAuth2Request();
    result.tenantId = tenantId;
    result.alpha = alpha;
    result.beta = beta;

    return Response.status(Response.Status.OK).entity(result).build();
  }

  public class OAuth2Request {
    protected String tenantId;
    protected String alpha;
    protected String beta;
  }
}

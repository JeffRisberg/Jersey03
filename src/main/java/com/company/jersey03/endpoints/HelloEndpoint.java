package com.company.jersey03.endpoints;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api(value = "Hello", description = "Simple example endpoint")
@Singleton
@Path("hello")
public class HelloEndpoint {

    @Inject
    public HelloEndpoint() {
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Say hello", response = String.class)
    public Response handle() {
        String results = "Hello There";
        return Response.status(Response.Status.OK).entity(results).build();
    }
}

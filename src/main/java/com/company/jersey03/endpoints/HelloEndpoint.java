package com.company.jersey03.endpoints;

import io.swagger.annotations.Api;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Api
@Singleton
@Path("hello")
public class HelloEndpoint {

    @Inject
    public HelloEndpoint() {
    }

    @GET
    public Response handle() {
        Object results = "Hello There";
        return Response.status(Response.Status.OK).entity(results).build();
    }
}

package com.akanto.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.akanto.hello.Greeting;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

// endpoint contains oly jax-rs annotation

@Path("/greeting")
@Api(value = "/greeting", description = "Greetings Endpoint", position = 1)
public interface GreetingEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "say hello", produces = MediaType.APPLICATION_JSON)
    Greeting greeting();

    @GET
    @Path("/quick")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "say hello to sby", produces = MediaType.APPLICATION_JSON)
    Greeting greetingQuick(@QueryParam(value = "name") String name);

    @GET
    @Path("/slow")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "say hello to sby", produces = MediaType.APPLICATION_JSON)
    Greeting greetingSlow(@QueryParam(value = "name") String name);

}
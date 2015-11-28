package com.akanto.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.akanto.hello.Greeting;

// endpoint contains oly jax-rs annotation

@Path("/greeting")
public interface GreetingEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Greeting greeting(@QueryParam(value = "name") String name);


    @GET
    @Path("/error")
    @Produces(MediaType.APPLICATION_JSON)
    Greeting error();


}
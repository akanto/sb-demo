package com.akanto.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.akanto.hello.Greeting;

// endpoint contains oly jax-rs annotation

@Path("/akka")
public interface AkkaEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Greeting greetingAkka();


}
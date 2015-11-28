package com.akanto.controller;

import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.akanto.hello.Greeting;

@Component
@Path("/greeting")
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Greeting greeting(@QueryParam(value = "name") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }


}
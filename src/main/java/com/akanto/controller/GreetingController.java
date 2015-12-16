package com.akanto.controller;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.akanto.api.GreetingEndpoint;
import com.akanto.hello.Greeting;
import com.akanto.service.GreetingService;


// controller contains only spring annotation

@Controller
public class GreetingController implements GreetingEndpoint {

    private Logger log = LoggerFactory.getLogger(GreetingController.class);

    @Inject
    private GreetingService greetingService;

    @Override
    public Greeting greeting(String name) {
        return greetingService.greeting(name);
    }

    @Override
    public Greeting error() {
        throw new WebApplicationException("Some message with a status e.g. forbidden",
                Response.Status.FORBIDDEN);
    }


}
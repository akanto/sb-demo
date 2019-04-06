package com.akanto.controller;

import javax.inject.Inject;

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


    public GreetingController() {
        log.debug("Hello");
    }

    @Override
    public Greeting greeting() {
        return new Greeting(0, "No name has provided, please use /greeting/quick?name=World", null);
    }

    @Override
    public Greeting greetingQuick(String name) {
        return greetingService.greetingWithCache(name);
    }

    @Override
    public Greeting greetingSlow(String name, Long delay) {
        return greetingService.greetingNoCache(name, delay);
    }

}
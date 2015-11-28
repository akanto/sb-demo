package com.akanto.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;

import com.akanto.api.GreetingEndpoint;
import com.akanto.hello.Greeting;


// controller contains only spring annotation

@Controller
public class GreetingController implements GreetingEndpoint {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Override
    public Greeting greeting(String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }


}
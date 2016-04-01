package com.akanto.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.akanto.api.AkkaEndpoint;
import com.akanto.hello.Greeting;
import com.akanto.service.AkkaService;


// controller contains only spring annotation

@Controller
public class AkkaController implements AkkaEndpoint {

    private Logger log = LoggerFactory.getLogger(AkkaController.class);

    @Inject
    private AkkaService akkaService;

    @Override
    public Greeting greetingAkka() {
        return akkaService.greetingAkka();
    }


}
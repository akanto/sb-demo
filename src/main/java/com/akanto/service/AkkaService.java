package com.akanto.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.akanto.hello.Greeting;

@Service
public class AkkaService {

    private Logger log = LoggerFactory.getLogger(AkkaService.class);

    @Transactional
    public Greeting greetingAkka() {
        String hello = "Hello Akka! Pi: ";
        log.debug(hello);
        return new Greeting(0, hello);
    }

}

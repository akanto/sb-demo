package com.akanto.service;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.akanto.akka.Pi;
import com.akanto.hello.Greeting;

@Service
public class AkkaService {

    private Logger log = LoggerFactory.getLogger(AkkaService.class);

    @Inject
    private Pi pi;

    @Transactional
    public Greeting greetingAkka() {
        String hello = "Hello Akka! Pi: %s";
        log.debug(hello);
 //       pi.calculate(4, 10000, 10);
        try {
            hello = String.format(hello, pi.calculateSync(4, 1000, 10).getPi());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Greeting(0, hello);
    }

}

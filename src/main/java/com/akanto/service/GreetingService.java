package com.akanto.service;

import java.util.concurrent.atomic.AtomicLong;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.akanto.hello.Greeting;

@Service
public class GreetingService {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private Logger log = LoggerFactory.getLogger(GreetingService.class);


    @Transactional
    @Cacheable(cacheNames="greetings", key="#name")
    public Greeting greeting(String name) {
        log.debug("Greeting invoked. name: {}", name);

        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

}

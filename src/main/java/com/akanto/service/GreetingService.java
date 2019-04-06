package com.akanto.service;

import java.util.concurrent.ThreadLocalRandom;
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
    @Cacheable(cacheNames = "greetings", key = "#name")
    public Greeting greetingWithCache(String name) {
        log.debug("Greeting invoked. name: {}", name);

        return greetingNoCache(name, 0L);
    }

    @Transactional
    public Greeting greetingNoCache(String name, Long delay) {
        log.debug("Greeting invoked. name: {}", name);

        long sleep;

        if (delay == null) {
            sleep = ThreadLocalRandom.current().nextLong(10000);
        } else {
            sleep = delay;
        }
        log.info("Sleeping for {}", sleep);

        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            log.info(e.getMessage(), e);
        }

        return new Greeting(counter.incrementAndGet(),
                String.format(template, name), sleep);
    }
}

package com.akanto.service;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.akanto.hello.Greeting;

import io.opentracing.ActiveSpan;
import io.opentracing.util.GlobalTracer;

@Service
public class GreetingService {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private Logger log = LoggerFactory.getLogger(GreetingService.class);

    @Transactional
    public void slowMockDbCall() {
        log.debug("NotCachedGreeting invoked. name");


        try (ActiveSpan span = GlobalTracer.get().buildSpan("slowMockDbCall").startActive()) {
            try {
                int random = new Random().nextInt(300);
                Thread.sleep(random);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Transactional
    @Cacheable(cacheNames="greetings", key="#name")
    public Greeting greeting(String name) {
        log.debug("Greeting invoked. name: {}", name);

        int random = new Random().nextInt(100);

        try (ActiveSpan span = GlobalTracer.get().buildSpan("greeting").startActive()) {
            try {
                Thread.sleep(random);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

}

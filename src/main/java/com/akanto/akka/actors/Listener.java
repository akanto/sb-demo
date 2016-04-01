package com.akanto.akka.actors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.akanto.akka.messages.PiApproximation;

import akka.actor.UntypedActor;

public class Listener extends UntypedActor {

    private Logger log = LoggerFactory.getLogger(Listener.class);

    public void onReceive(Object message) {
        log.info("Listener: {}", message);
        if (message instanceof PiApproximation) {
            PiApproximation approximation = (PiApproximation) message;
            log.info("Listener: Pi approximation: {}; Calculation time: {}", approximation.getPi(), approximation.getDuration());
            getContext().system().shutdown();
        } else {
            unhandled(message);
        }
    }
}
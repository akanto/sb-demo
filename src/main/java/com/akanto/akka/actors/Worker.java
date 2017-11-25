package com.akanto.akka.actors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.akanto.akka.base.ClusteredUntypedActor;
import com.akanto.akka.messages.Result;
import com.akanto.akka.messages.Work;

import io.opentracing.ActiveSpan;
import io.opentracing.util.GlobalTracer;
import scala.Option;

public class Worker extends ClusteredUntypedActor {

    private static Logger log = LoggerFactory.getLogger(Worker.class);


    public Worker() {
        log.info("Worker instantiated: {}", this);
    }


    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        super.preRestart(reason, message);
        if (message.isDefined()) {
            log.info("Resend message: {}", message.get());
            getSelf().tell(message.get(), getSender());
        } else {
            log.warn("All actor recives, they can safely ignore it: {}, message: {}", reason.getMessage(), message);
        }
    }

    // calculatePiFor ...
    public void onReceive(Object message) {
        log.info("Worker message received: {}", message);
        if (message instanceof Work) {
            Work work = (Work) message;
            try (ActiveSpan span = GlobalTracer.get().buildSpan("akkaWork").asChildOf(work.getActiveSpan()).startActive()) {
                double result = calculatePiFor(work.getStart(), work.getNrOfElements());
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            if (Math.random() > 1.9) {
//                log.warn("Something bad is going to happen at: {}", message);
//                throw new ArithmeticException(String.format("Something bad has happened at: %d", work.getStart()));
//            }
                getSender().tell(new Result(result), getSelf());
            }
        } else {
            unhandled(message);
        }
    }

    private double calculatePiFor(int start, int nrOfElements) {
        double acc = 0.0;
        for (int i = start * nrOfElements; i <= ((start + 1) * nrOfElements - 1); i++) {
            acc += 4.0 * (1 - (i % 2) * 2) / (2 * i + 1);
        }
        return acc;
    }
}

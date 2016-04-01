package com.akanto.akka;

import java.util.concurrent.TimeUnit;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinPool;
import scala.concurrent.duration.Duration;

public class Master extends UntypedActor {
    private final int nrOfMessages;
    private final int nrOfElements;
    private final long start = System.currentTimeMillis();
    private final ActorRef listener;
    private final ActorRef workerRouter;
    private double pi;
    private int nrOfResults;

    public Master(final int nrOfWorkers, int nrOfMessages, int nrOfElements, ActorRef listener) {
        this.nrOfMessages = nrOfMessages;
        this.nrOfElements = nrOfElements;
        this.listener = listener;

        workerRouter = this.getContext().actorOf(new RoundRobinPool(nrOfWorkers).props(Props.create(Worker.class)), "workerRouter");
    }

    public void onReceive(Object message) {
        if (message instanceof Calculate) {
            for (int start = 0; start < nrOfMessages; start++) {
                workerRouter.tell(new Work(start, nrOfElements), getSelf());
            }
        } else if (message instanceof Result) {
            Result result = (Result) message;
            pi += result.getValue();
            nrOfResults += 1;
            if (nrOfResults == nrOfMessages) {
                // Send the result to the listener
                Duration duration = Duration.create(System.currentTimeMillis() - start, TimeUnit.MILLISECONDS);
                listener.tell(new PiApproximation(pi, duration), getSelf());
                // Stops this actor and all its supervised children
                getContext().stop(getSelf());
            }
        } else {
            unhandled(message);
        }
    }
}
package com.akanto.akka.actors;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.akanto.akka.messages.Calculate;
import com.akanto.akka.messages.PiApproximation;
import com.akanto.akka.messages.Result;
import com.akanto.akka.messages.Work;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinPool;
import scala.concurrent.duration.Duration;

public class MasterSync extends UntypedActor {

    private final int nrOfMessages;
    private final int nrOfElements;
    private final long start = System.currentTimeMillis();

    private ActorRef original;
    private final ActorRef workerRouter;
    private Logger log = LoggerFactory.getLogger(MasterSync.class);
    private double pi;
    private int nrOfResults;

    public MasterSync(final int nrOfWorkers, int nrOfMessages, int nrOfElements) {
        this.nrOfMessages = nrOfMessages;
        this.nrOfElements = nrOfElements;

        workerRouter = this.getContext().actorOf(new RoundRobinPool(nrOfWorkers).props(Props.create(Worker.class)), "workerRouter");
        log.info("Master instantiated: {}", this);
    }

    public void onReceive(Object message) {
        log.info("Master message received: {}", message);
        if (message instanceof Calculate) {
            original = getSender();
            for (int start = 0; start < nrOfMessages; start++) {
                workerRouter.tell(new Work(start, nrOfElements, ((Calculate) message).getActiveSpan()), getSelf());
            }
        } else if (message instanceof Result) {
            Result result = (Result) message;
            pi += result.getValue();
            nrOfResults += 1;
            if (nrOfResults == nrOfMessages) {
                // Send the result to the listener
                Duration duration = Duration.create(System.currentTimeMillis() - start, TimeUnit.MILLISECONDS);
                original.tell(new PiApproximation(pi, duration), getSelf());
                //getContext().lookupRoot().tell(new PiApproximation(pi, duration), getSelf());
                //Patterns.pipe(Futures.successful(new PiApproximation(pi, duration)), getContext().system().dispatcher()).to(getContext().guardian());


                // Stops this actor and all its supervised children
                getContext().stop(getSelf());
            }
        } else {
            unhandled(message);
        }
    }

    @Override
    public String toString() {
        return "Master{" +
                "nrOfMessages=" + nrOfMessages +
                ", nrOfElements=" + nrOfElements +
                ", start=" + start +
                ", workerRouter=" + workerRouter +
                ", pi=" + pi +
                ", nrOfResults=" + nrOfResults +
                '}';
    }
}
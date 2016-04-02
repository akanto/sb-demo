package com.akanto.akka;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.akanto.akka.actors.Listener;
import com.akanto.akka.actors.Master;
import com.akanto.akka.actors.MasterSync;
import com.akanto.akka.messages.Calculate;
import com.akanto.akka.messages.PiApproximation;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

@Service
public class Pi {

    // actors and messages ...
    public void calculate(final int nrOfWorkers, final int nrOfElements, final int nrOfMessages) {
        // Create an Akka system
        ActorSystem system = ActorSystem.create("PiSystem");

        // create the result listener, which will print the result and shutdown the system
        //final ActorRef listener = system.actorOf(new RoundRobinPool(1).props(Props.create(Listener.class)), "listener");
        final ActorRef listener = system.actorOf(Props.create(Listener.class), "listener");
        // create the master
        ActorRef master = system.actorOf(Props.create(Master.class, nrOfWorkers, nrOfMessages, nrOfElements, listener), "master");
        master.tell(new Calculate(), ActorRef.noSender());

    }



    public PiApproximation calculateSync(final int nrOfWorkers, final int nrOfElements, final int nrOfMessages) throws Exception {
        // Create an Akka system
        ActorSystem system = ActorSystem.create("PiSystem");

        // create the master
        ActorRef master = system.actorOf(Props.create(MasterSync.class, nrOfWorkers, nrOfMessages, nrOfElements), "master");

        Timeout timeout = new Timeout(Duration.create(10, TimeUnit.SECONDS));
        Future<Object> future = Patterns.ask(master, new Calculate(), timeout);
        return (PiApproximation) Await.result(future, timeout.duration());

    }




}

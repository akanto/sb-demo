package com.akanto.akka;

import org.springframework.stereotype.Service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

@Service
public class Pi {

    public void calculate() {
        Pi pi = new Pi();
        pi.calculate(4, 10000, 10000);
    }

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


}

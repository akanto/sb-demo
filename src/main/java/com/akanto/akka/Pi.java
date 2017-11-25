package com.akanto.akka;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.akanto.akka.actors.Listener;
import com.akanto.akka.actors.Master;
import com.akanto.akka.actors.MasterSync;
import com.akanto.akka.messages.Calculate;
import com.akanto.akka.messages.PiApproximation;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.singleton.ClusterSingletonManager;
import akka.cluster.singleton.ClusterSingletonManagerSettings;
import akka.pattern.Patterns;
import akka.routing.RoundRobinPool;
import akka.util.Timeout;
import io.opentracing.ActiveSpan;
import io.opentracing.util.GlobalTracer;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

@Service
public class Pi {

    @Inject
    private ActorSystem actorSystem;

    private AtomicInteger cntr = new AtomicInteger();

    @Value("akka.remote.netty.tcp.port")
    private String someId;

    // actors and messages ...
    public void calculate(final int nrOfWorkers, final int nrOfElements, final int nrOfMessages) {
//        // Create an Akka system
//        ActorSystem system = ActorSystem.create("ClusterSystem");

        //final ClusterSingletonManagerSettings settings =
        //ClusterSingletonManagerSettings.create(actorSystem).withRole("worker");



        // create the result listener, which will print the result and shutdown the system
        final ActorRef listener = actorSystem.actorOf(new RoundRobinPool(1).props(Props.create(Listener.class)), "listener");
        // final ActorRef listener = actorSystem.actorOf(ClusterSingletonManager.props(Props.create(Listener.class), null ,settings), "listener-" + cntr.incrementAndGet());
        // create the master
        ActorRef master = actorSystem.actorOf(Props.create(Master.class, nrOfWorkers, nrOfMessages, nrOfElements, listener), "master-" + cntr.incrementAndGet());
        master.tell(new Calculate(null), ActorRef.noSender());

    }

    // actors and messages ...
    public void calculatex(final int nrOfWorkers, final int nrOfElements, final int nrOfMessages) {
//        // Create an Akka system
//        ActorSystem system = ActorSystem.create("ClusterSystem");

        // create the result listener, which will print the result and shutdown the system
        //final ActorRef listener = system.actorOf(new RoundRobinPool(1).props(Props.create(Listener.class)), "listener");
        final ActorRef listener = actorSystem.actorOf(Props.create(Listener.class), "listener-" + cntr.incrementAndGet());
        // create the master
        ActorRef master = actorSystem.actorOf(Props.create(Master.class, nrOfWorkers, nrOfMessages, nrOfElements, listener), "master-" + cntr.incrementAndGet());
        master.tell(new Calculate(null), ActorRef.noSender());

    }



    public PiApproximation calculateSync(final int nrOfWorkers, final int nrOfElements, final int nrOfMessages) throws Exception {
        try (ActiveSpan span = GlobalTracer.get().buildSpan("calculateSync").startActive()) {


            // create the master
            ActorRef master = actorSystem.actorOf(Props.create(MasterSync.class, nrOfWorkers, nrOfMessages, nrOfElements), "master");


            Timeout timeout = new Timeout(Duration.create(10, TimeUnit.SECONDS));
            Future<Object> future = Patterns.ask(master, new Calculate(span), timeout);
            return (PiApproximation) Await.result(future, timeout.duration());
        }

    }




}

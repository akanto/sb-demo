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


    public PiApproximation calculateSync(final int nrOfWorkers, final int nrOfElements, final int nrOfMessages) throws Exception {
        try (ActiveSpan span = GlobalTracer.get().buildSpan("calculateSync").startActive()) {


            // create the master
            ActorRef master = actorSystem.actorOf(Props.create(MasterSync.class, nrOfWorkers, nrOfMessages, nrOfElements), "master");


            Timeout timeout = new Timeout(Duration.create(30, TimeUnit.SECONDS));
            Future<Object> future = Patterns.ask(master, new Calculate(span), timeout);
            return (PiApproximation) Await.result(future, timeout.duration());
        }

    }




}

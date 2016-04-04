package com.akanto.akka.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent.MemberUp;

public abstract class ClusteredUntypedActor extends UntypedActor {

    private static Logger log = LoggerFactory.getLogger(ClusteredUntypedActor.class);

    Cluster cluster = Cluster.get(getContext().system());


    @Override
    public void preStart() throws Exception {
        cluster.subscribe(getSelf(), MemberUp.class);
    }

    //re-subscribe when restart
    @Override
    public void postStop() {
        cluster.unsubscribe(getSelf());
    }

}

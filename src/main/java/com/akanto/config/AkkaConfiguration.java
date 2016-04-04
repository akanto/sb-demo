package com.akanto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import akka.actor.ActorSystem;

@Configuration
public class AkkaConfiguration {
    @Bean
    public ActorSystem actorSystem() {
        // Create an Akka system
        return ActorSystem.create("ClusterSystem");
    }

}

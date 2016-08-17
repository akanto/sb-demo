package com.akanto.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.akanto.controller.AkkaController;
import com.akanto.controller.GreetingController;
import com.akanto.controller.SaltController;

import io.swagger.jaxrs.listing.ApiListingResource;

@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        configureSwagger();
        registerEndpoints();
    }

    private void configureSwagger() {
        register(ApiListingResource.class);
    }

    private void registerEndpoints() {
        register(GreetingController.class);
        register(AkkaController.class);
        register(SaltController.class);
    }
}
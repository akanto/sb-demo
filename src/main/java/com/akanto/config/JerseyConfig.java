package com.akanto.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.akanto.controller.AkkaController;
import com.akanto.controller.GreetingController;
import com.akanto.controller.SaltController;
import com.uber.jaeger.Configuration;
import com.uber.jaeger.filters.jaxrs2.TracingUtils;
import com.uber.jaeger.samplers.ProbabilisticSampler;

import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;
import io.swagger.jaxrs.listing.ApiListingResource;

@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        configureSwagger();
        globalTracer();
        registerEndpoints();
    }

    private void configureSwagger() {
        register(ApiListingResource.class);
    }

    private void registerEndpoints() {
        register(GreetingController.class);
        register(AkkaController.class);
        register(SaltController.class);
        register(TracingUtils.serverFilter(GlobalTracer.get()));
    }

    private void globalTracer() {
        GlobalTracer.register(
                new Configuration(
                        "sb-demo",
                        new Configuration.SamplerConfiguration(ProbabilisticSampler.TYPE, 1),
                        new Configuration.ReporterConfiguration(
                                false, "192.168.99.100", 5775, 1000, 10000)
                ).getTracer());

    }
}
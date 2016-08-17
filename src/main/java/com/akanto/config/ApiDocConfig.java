package com.akanto.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import io.swagger.jaxrs.config.BeanConfig;

@Configuration
public class ApiDocConfig {

    @Bean
    public BeanConfig swaggerBeanConfig() throws IOException {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setTitle("SB Demo API");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setBasePath("/");
        beanConfig.setResourcePackage("com.akanto.api");
        beanConfig.setScan(true);
        return beanConfig;
    }
}

package com.bp.var.scenario;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by rothb1 on 22/07/2016.
 */
public class ScenarioApplication extends Application {

    public ScenarioApplication() {
        BeanConfig beanConfig = new BeanConfig();

        beanConfig.setTitle("Scenario Microservice");
        beanConfig.setDescription("Produces market scenarios, either random ones based on historic data or the official market scenario based on latest price observations");
        beanConfig.setContact("Bruno Rothgiesser");
        beanConfig.setVersion("1.0.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8085");
        beanConfig.setBasePath("/marketdata-1.0/rest");
        beanConfig.setResourcePackage("com.bp.var.scenario");
        beanConfig.setScan(true);
    }


    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<Class<?>>();
        s.add(ScenarioService.class);
        s.add(ApiListingResource.class);
        s.add(SwaggerSerializers.class);
        return s;
    }

}

package com.softwarelab.softwarelabelectroniclogbookwebservice.web.properties;

import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Component;

/**
 * Created by Wilson
 * on Tue, 11/05/2021.
 */
@Component
public class ApplicationProperty {
    StandardEnvironment environment;

    public ApplicationProperty(StandardEnvironment environment) {
        this.environment = environment;
    }

    public long getMaxTokenLifeInMinutes(){
        return Long.parseLong(environment.getProperty("max.token.life.in.mins","30"));
    }
    public String getApiRestKey(){
        return environment.getProperty("api.client.key","");
    }
}

package com.microservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import javax.annotation.PostConstruct;


@Configuration
@PropertySources({@PropertySource("classpath:httpRequest.properties")})
public class HttpRequestLendingManagementProperties {
    private static HttpRequestLendingManagementProperties instance;
    @Value("${http.request.lending.management}")
    private String httpRequestLendingManagement;

    public static HttpRequestLendingManagementProperties getInstance() {
        return instance;
    }

    @PostConstruct
    private void init() {
        instance = this;
    }

    public String getHttpRequestLendingManagement() {
        return httpRequestLendingManagement;
    }


}

package com.microservice.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.microservice.configuration.HttpRequestLendingManagementProperties;

@Configuration
@PropertySource("classpath:httpRequest.properties")
public class HttpRequestLendingManagementPropertiesTest {

    private HttpRequestLendingManagementProperties properties;

    @Before
    public void setUp() {
        properties = new HttpRequestLendingManagementProperties();
    }

    @Test
    public void testGetHttpRequestLendingManagement() {
        assertNotNull(properties);
    }
}

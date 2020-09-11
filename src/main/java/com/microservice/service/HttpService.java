package com.microservice.service;

import com.microservice.configuration.HttpRequestLendingManagementProperties;
import com.microservice.model.User;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class HttpService {

    private final RestTemplate restTemplate;

    public HttpService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public User[] getExpiredUsers(String url) {
        return this.restTemplate.getForObject(url, User[].class);
    }
}

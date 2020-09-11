package com.microservice.service;

import com.microservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class HttpService {

    @Autowired
    private final RestTemplate restTemplate;

    public HttpService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> getExpiredUsers(String url) {
        return Arrays.asList(this.restTemplate.getForObject(url, User[].class));
    }
}

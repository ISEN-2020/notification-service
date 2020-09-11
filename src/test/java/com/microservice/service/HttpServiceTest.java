package com.microservice.service;

import com.microservice.model.Book;
import com.microservice.model.User;
import com.microservice.service.HttpService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@RunWith(MockitoJUnitRunner.class)
public class HttpServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private HttpService httpService;

    @Test
    public void getExpiredUsersTest() {
        User[] users = new User[] { new User("Jon Doe", "", Collections.singletonList(new Book("Book name"))) };

        Mockito.when(restTemplate.getForObject("", User[].class))
                .thenReturn(users);

        Assert.assertArrayEquals(users,httpService.getExpiredUsers("").toArray());
    }
}

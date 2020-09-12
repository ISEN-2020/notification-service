package com.microservice.service;

import com.microservice.model.Book;
import com.microservice.model.BookRow;
import com.microservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HttpService {

    @Autowired
    private final RestTemplate restTemplate;

    public HttpService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<BookRow> getExpiredUsers(String url) {
        return Arrays.asList(this.restTemplate.getForObject(url, BookRow[].class));
    }

    public List<User> managementJsonToUserArrayJson(List<BookRow> booksRow) {
        List<User> users = new ArrayList<>();

        for(String userName : booksRow.stream().map(BookRow::getName).distinct().collect(Collectors.toList())) {
            String email = booksRow.stream().filter(row -> row.getName().equals(userName)).map(n -> n.getMail()).findFirst().get();
            users.add(new User(userName,email,booksRow.stream().filter(row -> row.getName().equals(userName)).map(n -> new Book(n.getBook(),n.getDate()) ).collect(Collectors.toList())));
        }
        return users;
    }
}

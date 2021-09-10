package com.microservice.service;

import com.microservice.model.Book;
import com.microservice.model.BookRow;
import com.microservice.model.User;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HttpService {

 //   private final RestTemplate restTemplate = new RestTemplate();

    public HttpService() {

    }

    public List<BookRow> getExpiredUsers(String url) {
    /*
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity request = new HttpEntity(httpHeaders);

        RestTemplate rt = new RestTemplate();
        ResponseEntity<BookRow[]> response = rt.exchange(url, HttpMethod.GET, request, BookRow[].class);
        BookRow[] books = response.getBody();

     */
        RestTemplate restTemplate = new RestTemplate();

        try{
            BookRow[] books = restTemplate.getForObject(url, BookRow[].class);
            return Arrays.asList(books);
        }catch(NullPointerException ex) {
            throw new NullPointerException(ex.getMessage());
        }
    }

    public List<User> managementJsonToUserArrayJson(List<BookRow> booksRow) {
        List<User> users = new ArrayList<>();

        for (String userName : booksRow.stream().map(BookRow::getName).distinct().collect(Collectors.toList())) {
            String email = booksRow.stream().filter(row -> row.getName().equals(userName)).map(n -> n.getEmailAddress()).findFirst().orElse(null);
            users.add(new User(userName, email, booksRow.stream().filter(row -> row.getName().equals(userName)).map(n -> new Book(n.getBook(), n.getDate())).collect(Collectors.toList())));
        }
        return users;
    }
}

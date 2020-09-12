package com.microservice.model;

import java.util.List;


public class User {
    private final String name;
    private final String emailAddress;
    private final List<Book> expiredBooks;

    public User(String name, String emailAddress, List<Book> expiredBooks) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.expiredBooks = expiredBooks;
    }

    public String getName() {
        return this.name;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public List<Book> getExpiredBooks() {
        return this.expiredBooks;
    }
}
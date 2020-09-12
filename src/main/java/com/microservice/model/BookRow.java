package com.microservice.model;

import java.util.Date;

public class BookRow {
    private final String book;
    private final String name;
    private final String mail;
    private final Date date;

    public BookRow(String book, String name, String mail, Date date) {
        this.book = book;
        this.name = name;
        this.mail = mail;
        this.date = date;
    }

    public String getBook() {
        return this.book;
    }

    public String getName() {
        return this.name;
    }

    public String getMail() {
        return this.mail;
    }

    public Date getDate() {
        return this.date;
    }
}

package com.microservice.model;

import java.util.Date;

public class BookRow {
    private String book;
    private String name;
    private String mail;
    private Date date;

    public BookRow(String book, String name, String mail, Date date) {
        this.book = book;
        this.name = name;
        this.mail = mail;
        this.date = date;
    }
    public String getBook(){
        return this.book;
    }

    public String getName(){
        return this.name;
    }

    public String getMail(){
        return this.mail;
    }

    public Date getDate(){
        return this.date;
    }
}

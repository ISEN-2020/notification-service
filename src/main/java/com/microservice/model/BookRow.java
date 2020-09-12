package com.microservice.model;

import java.util.Date;

public class BookRow {
    private  int id;
    private  String book;
    private  String name;
    private  String emailAddress;
    private  Date date;

    public BookRow(){

    }
    public BookRow(int id, String book, String name, String emailAddress, Date date) {
        this.id=id;
        this.book = book;
        this.name = name;
        this.emailAddress = emailAddress;
        this.date = date;
    }

    public int getId(){
        return this.id;
    }

    public String getBook() {
        return this.book;
    }

    public String getName() {
        return this.name;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public Date getDate() {
        return this.date;
    }
}

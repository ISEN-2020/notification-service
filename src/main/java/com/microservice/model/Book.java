package com.microservice.model;


import java.util.Date;

public class Book {
	private String name;
	private Date date;

	public Book(String name, Date date) {
		this.name = name;
		this.date = date;
	}

	public String getName() {
		return this.name;
	}

	public Date getDate() {
		return this.date;
	}
}

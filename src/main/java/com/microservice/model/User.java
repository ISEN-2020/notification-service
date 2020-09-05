package com.microservice.model;

import java.util.List;


public class User {
	private String name;
	private String emailAddress;
	private List<Book> expiredBooks;
	
	
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
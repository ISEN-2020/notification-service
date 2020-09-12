package com.microservice.application;

import com.microservice.model.Book;
import com.microservice.model.BookRow;
import com.microservice.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication(scanBasePackages = {"com.microservice"})
@EnableScheduling
public class Application {
	
	public static void  main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
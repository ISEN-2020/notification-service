package com.microservice.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.microservice"})
@EnableScheduling
public class Application {
	
	public static void  main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
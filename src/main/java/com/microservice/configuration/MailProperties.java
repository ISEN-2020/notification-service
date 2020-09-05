package com.microservice.configuration;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import com.microservice.model.Book;


@Configuration
@PropertySources({@PropertySource("classpath:mailConfig.properties")})
public class MailProperties
{
	@Value("${mail.subject.message}")
	private String mailSubjectMessage;
	@Value("${mail.text.message}")
	private String mailTextMessage;
	
	
	private static MailProperties instance;
	
	
	@PostConstruct
	private void init()
	{
		instance=this;
	}
	
	public String getMailSubjectMessage(String name)
	{
		return MessageFormat.format(mailSubjectMessage, name);
	}
	
	public String getMailTextMessage(List<Book> expiredBooks)
	{
		return MessageFormat.format(mailTextMessage, expiredBooks.stream().map(Book::getName).collect(Collectors.toList()));
	}
	

	public static MailProperties getInstance()
	{
		return instance;
	}
	
	
	
}

package com.microservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.microservice.configuration.MailProperties;
import com.microservice.model.User;

@Service
public class MailService {

	private JavaMailSender javaMailSender;
	
	@Autowired
	public MailService(JavaMailSender javaMailSender) {
		this.javaMailSender=javaMailSender;
	}
	
	public void sendEmail(User user) throws MailException {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmailAddress());
		mail.setSubject(MailProperties.getInstance().getMailSubjectMessage(user.getName()));
		mail.setText(MailProperties.getInstance().getMailTextMessage(user.getExpiredBooks()));
		javaMailSender.send(mail);
	}
}

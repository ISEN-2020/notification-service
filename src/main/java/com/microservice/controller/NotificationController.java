package com.microservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.microservice.model.User;
import com.microservice.service.MailService;

@RestController
public class NotificationController {
	
	@Autowired
	private MailService mailService;
	
	@RequestMapping(value="/sendEmail",method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<HttpStatus> postSendMail(@RequestBody List<User> postData)
	{
		for(User user : postData)
		{
			try 
			{
				mailService.sendEmail(user);
			} 
			catch (MailException e) 
			{
				throw new InternalError(e.getMessage());
			}
		}
		return ResponseEntity.ok(HttpStatus.valueOf(200));
	}
}
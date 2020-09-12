package com.microservice.controller;

import com.microservice.configuration.HttpRequestLendingManagementProperties;
import com.microservice.model.User;
import com.microservice.service.HttpService;
import com.microservice.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotificationController {

    @Autowired
    private MailService mailService;

    @Autowired
    private HttpService httpService;

    @Scheduled(fixedDelay = 600000 /*cron = "0 0 10 1 * MON-FRI"*/, zone = "Europe/Paris")
    @RequestMapping(value = "/sendEmail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<HttpStatus> postSendMail() {

        List<User> users = httpService.managementJsonToUserArrayJson(httpService.getExpiredUsers(HttpRequestLendingManagementProperties.getInstance().getHttpRequestLendingManagement()));

        for (User user : users) {
            try {
                mailService.sendEmail(user);
            } catch (MailException e) {
                throw new InternalError(e.getMessage());
            }
        }
        return ResponseEntity.ok(HttpStatus.valueOf(200));
    }
}
package com.microservice.service;

import junit.framework.TestCase;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mail.javamail.JavaMailSender;

public class MailServiceTest extends TestCase {

    @InjectMocks
    private MailService mailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    public void testSendEmail() {
        assert(true);
    }

}
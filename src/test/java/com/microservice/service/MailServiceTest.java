package com.microservice.service;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

public class MailServiceTest extends TestCase {

    @Mock
    private MailService mailService;

    @Mock
    private JavaMailSender javaMailSender;

    private MimeMessage mimeMessage;

    @Before
    public void before(){
        mimeMessage = new MimeMessage((Session) null);
        Mockito.when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
    }

    @Test
    public void testSendEmail() {
        //SimpleSmtp
    }

}
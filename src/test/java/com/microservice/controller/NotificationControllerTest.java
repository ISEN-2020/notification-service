package com.microservice.controller;

import com.microservice.configuration.HttpRequestLendingManagementProperties;
import com.microservice.model.User;
import com.microservice.service.HttpService;
import com.microservice.service.MailService;
import com.microservice.model.BookRow;

//import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
//import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.mail.MailException;


import java.util.Collections;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class NotificationControllerTest {

    @InjectMocks
    private NotificationController notificationController;

    @Mock
    private MailService mailService;

    @Mock
    private HttpService httpService;

    @Mock
    private HttpRequestLendingManagementProperties properties;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPostSendMail_Success() {
        // Créez un utilisateur de test
        User testUser = new User("TestUser", "test@example.com", Collections.emptyList());

        //Creer une liste simuler de BookRow
        List<BookRow> simulatedBookRows = new ArrayList<>();
        simulatedBookRows.add(new BookRow(1, "Book 1", "User 1", "user1@example.com", new Date()));
        simulatedBookRows.add(new BookRow(2, "Book 2", "User 2", "user2@example.com", new Date()));

        // Mockez le comportement de httpService pour renvoyer une liste d'utilisateurs (uniquement le testUser)
        when(httpService.getExpiredUsers(anyString())).thenReturn((List<BookRow>) simulatedBookRows);

        // Mockez le comportement de mailService pour ne pas générer d'exception
        doNothing().when(mailService).sendEmail(any(User.class));

        // Appelez la méthode testée
        ResponseEntity<HttpStatus> responseEntity = notificationController.postSendMail();

        // Vérifiez que la réponse est OK (HTTP 200)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Vérifiez que mailService.sendEmail a été appelée avec le testUser
        verify(mailService, times(1)).sendEmail(testUser);
    }

    @Test(expected = InternalError.class)
    public void testPostSendMail_MailException() {
        // Mockez le comportement de httpService pour renvoyer une liste vide
        when(httpService.getExpiredUsers(anyString())).thenReturn(Collections.emptyList());

        // Mockez le comportement de mailService pour générer une MailException
       // doThrow(new MailException("Mail sending failed")) // Simule une exception
         //       .when(mailService).sendEmail(any(User.class));

        // Appelez la méthode testée
        notificationController.postSendMail();
    }
}

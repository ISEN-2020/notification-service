package com.microservice.configuration;

import org.junit.Before;
import org.junit.Test;
import com.microservice.model.Book;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MailPropertiesTest {

    private MailProperties mailProperties;

    @Before
    public void setUp() {
        mailProperties = new MailProperties();
    }

    @Test
    public void testGetMailSubjectMessage() {
        String name = "John";
        String expectedSubject = "Hello John!";
        String actualSubject = mailProperties.getMailSubjectMessage(name);
        assertEquals(expectedSubject, actualSubject);
    }

    @Test
    public void testGetMailTextMessage() {
        List<Book> expiredBooks = Arrays.asList(
                new Book("Book 1", new Date()),
                new Book("Book 2", new Date()),
                new Book("Book 3", new Date())
        );

        String expectedText = "You have the following expired books: [Book 1, Book 2, Book 3]";
        String actualText = mailProperties.getMailTextMessage(expiredBooks);
        assertEquals(expectedText, actualText);
    }
}

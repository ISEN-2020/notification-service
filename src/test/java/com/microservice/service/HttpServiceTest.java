package com.microservice.service;

import com.microservice.model.Book;
import com.microservice.model.BookRow;
import com.microservice.model.User;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class HttpServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private HttpService httpService;

    List<BookRow> bookRows;
    Date date;

    @Before
    public void before(){
        date = new Date();
        bookRows = new ArrayList<>();
        bookRows.add(new BookRow(1,"Game of Thrones", "Aria", "aria@gmail.com", date));
        bookRows.add(new BookRow(2,"Harry Potter 1", "Harry", "harry.potter@gmail.com", date));
        bookRows.add(new BookRow(3,"Harry Potter 3", "Harry", "harry.potter@gmail.com", date));
    }

    @Test
    public void managementJsonToUserArrayJsonTest() {
        List<User> userList = new ArrayList<>();
        userList.add(new User("Aria", "aria@gmail.com", Arrays.asList(new Book("Game of Thrones", date))));
        userList.add(new User("Harry", "harry@gmail.com", Arrays.asList(new Book("Harry Potter 1", date), new Book("Harry Potter 3", date))));

        List<User> jsonToUserArray = httpService.managementJsonToUserArrayJson(bookRows);

        assertEquals(userList.size(),jsonToUserArray.size());
        assertEquals(userList.get(0).getExpiredBooks().size(),jsonToUserArray.get(0).getExpiredBooks().size());
        assertEquals(userList.get(1).getExpiredBooks().size(),jsonToUserArray.get(1).getExpiredBooks().size());
        assertEquals(userList.get(1).getExpiredBooks().get(0).getName(),jsonToUserArray.get(1).getExpiredBooks().get(0).getName());
        assertEquals(userList.get(1).getExpiredBooks().get(1).getName(),jsonToUserArray.get(1).getExpiredBooks().get(1).getName());
        assertEquals(userList.get(1).getExpiredBooks().get(0).getDate(),jsonToUserArray.get(1).getExpiredBooks().get(0).getDate());
    }

    @Test
    public void getExpiredUsersTest() {
        //  BookRow[] bookRowArray = bookRows.toArray(new BookRow[0]);

    //    Mockito.when(restTemplate.getForObject("http://java.sun.com/j2se/1.3/", BookRow[].class))
   //             .thenReturn(bookRowArray);

 //       assertArrayEquals(bookRowArray, httpService.getExpiredUsers("http://java.sun.com/j2se/1.3/").toArray());
    }
}

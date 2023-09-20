package com.microservice.configuration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;;

@RunWith(SpringJUnit4ClassRunner.class)
public class HttpRequestLendingManagementPropertiesTest {

    private HttpRequestLendingManagementProperties properties;

    @Before
    public void setUp() {
        properties = HttpRequestLendingManagementProperties.getInstance();
        System.out.println("properties initialized: " + (properties != null));
    }

    @Test
    public void testGetHttpRequestLendingManagement() {
        System.out.println("Check 1");
        assertNotNull("it's work",properties);
        System.out.println("Check 2");

        // Vérifiez que la requête a réussi (par exemple, properties contient des données valides)
        // Adapter cette vérification en fonction de la logique de votre application
        assertTrue(properties.getHttpRequestLendingManagement() != null && !properties.getHttpRequestLendingManagement().isEmpty());

        // // Appel de la méthode que vous souhaitez tester
        String httpRequestLendingManagement = properties.getHttpRequestLendingManagement();

        // // Effectuez des assertions sur la valeur renvoyée
        //assertNotNull(httpRequestLendingManagement);
        // // Par exemple, vérifiez si la valeur est égale à quelque chose
        assertEquals("La valeur renvoyée doit correspondre à votre attente", "valeur attendue", httpRequestLendingManagement);
    }
}

package com.microservice.configuration;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HttpRequestLendingManagementPropertiesTest {

    private HttpRequestLendingManagementProperties properties;

    @Before
    public void setUp() {
        properties = HttpRequestLendingManagementProperties.getInstance();
    }

    @Test
    public void testGetHttpRequestLendingManagement() {
        assertNotNull(properties);

        // Appel de la méthode que vous souhaitez tester
        String httpRequestLendingManagement = properties.getHttpRequestLendingManagement();

        // Effectuez des assertions sur la valeur renvoyée
        assertNotNull(httpRequestLendingManagement);
        // Par exemple, vérifiez si la valeur est égale à quelque chose
        assertEquals("La valeur renvoyée doit correspondre à votre attente", "valeur attendue", httpRequestLendingManagement);
    }
}

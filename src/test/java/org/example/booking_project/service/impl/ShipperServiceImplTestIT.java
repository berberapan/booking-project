package org.example.booking_project.service.impl;

import org.example.booking_project.configs.IntegrationsProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShipperServiceImplTestIT {

    @Autowired
    JsonStreamProvider jsonStreamProvider;

    @Autowired
    IntegrationsProperties properties;

    @Test
    void shippersToJsonWillGetData() throws IOException {
        Scanner scanner = new Scanner(jsonStreamProvider.getDataStream(properties.shippers.fetchurl))
                .useDelimiter("\\A");
        String result = scanner.hasNext() ? scanner.next() : "";
        assert (!result.isEmpty());
        assertTrue(result.contains("id"));
        assertTrue(result.contains("email"));
        assertTrue(result.contains("companyName"));
        assertTrue(result.contains("contactName"));
        assertTrue(result.contains("streetAddress"));
        assertTrue(result.contains("city"));
        assertTrue(result.contains("postalCode"));
        assertTrue(result.contains("country"));
        assertTrue(result.contains("phone"));
        assertTrue(result.contains("fax"));
    }
}
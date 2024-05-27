package org.example.booking_project.service.impl;

import org.example.booking_project.configs.IntegrationsProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BlacklistServiceImplTestIT {

    @MockBean
    JsonStreamProvider jsonStreamProvider;
    @Autowired
    IntegrationsProperties properties;

    @Test
    void blacklistToJsonWillGetData() throws IOException {
        Scanner scanner = new Scanner(jsonStreamProvider.getDataStream(properties.blacklist.fetchUrl))
                .useDelimiter("\\A");
        String result = scanner.hasNext() ? scanner.next() : "";
        assert (!result.isEmpty());
        assertTrue(result.contains("id"));
        assertTrue(result.contains("email"));
        assertTrue(result.contains("name"));
        assertTrue(result.contains("group"));
        assertTrue(result.contains("created"));
        assertTrue(result.contains("ok"));
    }
}

package org.example.booking_project.service.impl;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.example.booking_project.Dtos.BlacklistedDTO;
import org.example.booking_project.configs.IntegrationsProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BlacklistServiceImplTests {

    @MockBean
    private JsonStreamProvider jsonStreamProvider;
    @Autowired
    BlacklistedServiceImpl blsi;
    @Autowired
    IntegrationsProperties properties;
    private WireMockServer wireMockServer;

    @BeforeEach
    void setUp() {
        blsi = new BlacklistedServiceImpl(jsonStreamProvider, properties);

        wireMockServer = new WireMockServer(wireMockConfig().port(8089));
        wireMockServer.start();
        WireMock.configureFor("localhost", 8089);

        properties.blacklist.setPostUrl("http://localhost:8089/blacklist");
        properties.blacklist.setPutUrl("http://localhost:8089/blacklist/");
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void blacklistMappedCorrectly() throws IOException {

        when(jsonStreamProvider.getDataStream(properties.blacklist.fetchUrl)).
                thenReturn(getClass().getClassLoader().getResourceAsStream("blacklistTestData.json"));

        BlacklistedDTO[] blacklistedDTOS = blsi.getBlacklistedArrayFromSource();

        assertEquals(13, blacklistedDTOS.length);
        assertEquals("stefan11111111@aaa.com", blacklistedDTOS[0].getEmail());
        assertEquals("Nandor Naan", blacklistedDTOS[3].getName());

    }

    @Test
    void checkIfUserExistsByEmail() throws IOException {

        when(jsonStreamProvider.getDataStream(properties.blacklist.fetchUrl)).
                thenReturn(getClass().getClassLoader().getResourceAsStream("blacklistTestData.json"));

        assertFalse(blsi.existsByEmail("finnsej@gmail.com"));
        assertTrue(blsi.existsByEmail("malin@hotmail.com"));
    }


    @Test
    void checkIfBlacklistedIsReadProperly() throws IOException {

        when(jsonStreamProvider.getDataStream(properties.blacklist.fetchUrl)).
                thenReturn(getClass().getClassLoader().getResourceAsStream("blacklistTestData.json"));

        assertTrue(blsi.checkIfCstBlacklisted("123@mail.se"));
        assertFalse(blsi.checkIfCstBlacklisted("malin@hotmail.com"));
    }

    @Test
    void checkIfBlacklistUpdatesCorrectly() throws IOException{
        BlacklistedDTO testUserblacklistedDTO = new BlacklistedDTO();
        testUserblacklistedDTO.setEmail("test123812@test.com");
        testUserblacklistedDTO.setName("Test user");
        testUserblacklistedDTO.setOk(true);

        stubFor(WireMock.post("/blacklist")
                .willReturn(WireMock.aResponse().
                        withStatus(200).withBody("Success")));

        blsi.updateBlacklisted(testUserblacklistedDTO);

        assertFalse(testUserblacklistedDTO.isOk());

        String expectedRequestBody = "{"

                + "\"email\":\"test123812@test.com\", "
                + "\"name\":\"Test user\", "
                + "\"ok\":false"
                + "}";

        verify(postRequestedFor(urlEqualTo("/blacklist"))
                .withRequestBody(equalToJson(expectedRequestBody)));


    }

}

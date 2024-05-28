package org.example.booking_project.service.impl;

import org.example.booking_project.configs.IntegrationsProperties;
import org.example.booking_project.models.Shipper;
import org.example.booking_project.repos.ShipperRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ShipperServiceImplTests {

    @MockBean
    private JsonStreamProvider jsonStreamProvider;
    private ShipperRepo shipperRepo = mock(ShipperRepo.class);
    @Autowired
    IntegrationsProperties properties;
    @Autowired
    ShipperServiceImpl sut;

    @BeforeEach
    void setUp() {
        sut = new ShipperServiceImpl(shipperRepo, jsonStreamProvider, properties);
    }

    @Test
    void shippersJsonMapperShouldMapCorrectly() throws IOException {
        when(jsonStreamProvider.getDataStream(properties.shippers.fetchurl))
                .thenReturn(getClass().getClassLoader().getResourceAsStream("testdata.json"));
        Shipper[] allShippers = sut.shippersJsonMapper();

        assertEquals(3, allShippers.length);
        assertEquals("karl.persson@hotmail.com", allShippers[0].getEmail());
        assertEquals(7, allShippers[1].getId());
        assertEquals("Johansson-Änglund", allShippers[2].getCompanyName());
        assertEquals("Nils Karlsson", allShippers[2].getContactName());
        assertEquals("plumber", allShippers[1].getContactTitle());
        assertEquals("Södra Furugatan 358", allShippers[1].getStreetAddress());
        assertEquals("Falberg", allShippers[1].getCity());
        assertEquals("10401", allShippers[1].getPostalCode());
        assertEquals("Sverige", allShippers[1].getCountry());
        assertEquals("070-992-7785", allShippers[0].getPhone());
        assertEquals("8463-68346", allShippers[0].getFax());
    }

    @Test
    void shippersToDatabaseShouldSaveNewShippers() {
        when(shipperRepo.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Shipper[] shippers = List.of(new Shipper(58L, "test@test.com", "test1", "test2", "test3", "test4", "test5", "12345", "test6", "00000", "11111"),
                new Shipper(59L, "test@test.com", "test1", "test2", "test3", "test4", "test5", "12345", "test6", "00000", "11111")).toArray(new Shipper[0]);

        sut.shippersToDatabase(shippers);

        verify(shipperRepo, times(1)).save(argThat(shipper -> shipper.getId() == 58L));
        verify(shipperRepo, times(1)).save(argThat(shipper -> shipper.getId() == 59L));

    }

    @Test
    void shippersToDatabaseShouldUpdateExistingShipperAndAddNewOne() {
        Shipper[] shippers = List.of(new Shipper(58L, "test@test.com", "test1", "test2", "test3", "test4", "test5", "12345", "test6", "00000", "11111"),
                new Shipper(59L, "test@test.com", "test1", "test2", "test3", "test4", "test5", "12345", "test6", "00000", "11111")).toArray(new Shipper[0]);
        when(shipperRepo.findById(59L)).thenReturn(Optional.of(shippers[1]));
        when(shipperRepo.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        sut.shippersToDatabase(shippers);

        verify(shipperRepo, times(1)).save(argThat(shipper -> shipper.getId() == 58L));
        verify(shipperRepo, times(1)).save(argThat(shipper -> shipper.getId() == 59L));
        assertEquals("test@test.com", shippers[1].getEmail());
    }

}
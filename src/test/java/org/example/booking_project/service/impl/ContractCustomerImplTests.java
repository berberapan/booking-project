package org.example.booking_project.service.impl;

import org.example.booking_project.Dtos.ContractCustomerDTO;
import org.example.booking_project.models.ContractCustomer;
import org.example.booking_project.repos.ContractCustomerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ContractCustomerImplTests {

    private XmlStreamProvider xmlStreamProvider;
    private ContractCustomerServiceImpl ccImpl;

    @BeforeEach
    void setUp() {
        ContractCustomerRepo contractCustomerRepo = mock(ContractCustomerRepo.class);
        xmlStreamProvider = mock(XmlStreamProvider.class);
        ccImpl = new ContractCustomerServiceImpl(contractCustomerRepo, xmlStreamProvider);
    }

    @Test
    void whenGetContractCustomerShouldMapCorrectly() throws Exception {
        // Arrange
        System.out.println("Mocking XmlStreamProvider...");
        when(xmlStreamProvider.getDataStream()).thenReturn(getClass().getResourceAsStream("/contractCustomer.xml"));
        System.out.println("XmlStreamProvider mocked successfully.");

        // Act
        System.out.println("Calling getAllContractCustomers...");
        List<ContractCustomerDTO> result = ccImpl.getContractCustomers();
        System.out.println("getAllContractCustomers called successfully.");

        // Assert
        System.out.println("Asserting results...");
        assertEquals(3, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("ABC", result.get(0).getCompanyName());
        assertEquals("John Doe", result.get(0).getContactName());
        assertEquals("Manager", result.get(0).getContactTitle());
        assertEquals("123 Main St", result.get(0).getStreetAddress());
        assertEquals("Springfield", result.get(0).getCity());
        assertEquals("12345", result.get(0).getPostalCode());
        assertEquals("USA", result.get(0).getCountry());
        assertEquals("555-123-4567", result.get(0).getPhone());
        assertEquals("555-987-6543", result.get(0).getFax());
        System.out.println("Assertions passed.");
    }
}

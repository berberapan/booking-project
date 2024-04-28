package org.example.booking_project.service.impl;

import org.example.booking_project.models.Customer;
import org.example.booking_project.repos.BookingRepo;
import org.example.booking_project.repos.CustomerRepo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CustomerServiceImplTest {

    @Mock
    private CustomerRepo customerRepo;

    @InjectMocks
    private CustomerServiceImpl customerService = new CustomerServiceImpl(customerRepo);

    private Customer testcustomer = new Customer((long) 123, "CN101", "Kalle",
            "012-345678", "abc@abcdef.se");

    @Test
    void generateCustomerNr() {
        when(customerRepo.findAll()).thenReturn(Arrays.asList(testcustomer));
        CustomerServiceImpl service2 = new CustomerServiceImpl(customerRepo);
        String testCustomerNr = service2.generateCustomerNr();
        assertEquals("CN102", testCustomerNr);
    }
}
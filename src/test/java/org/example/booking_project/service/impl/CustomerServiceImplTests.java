package org.example.booking_project.service.impl;

import org.example.booking_project.Dtos.CustomerDTO;
import org.example.booking_project.configs.IntegrationsProperties;
import org.example.booking_project.models.Customer;
import org.example.booking_project.repos.BookingRepo;
import org.example.booking_project.repos.CustomerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CustomerServiceImplTests {

    @Mock
    private CustomerRepo customerRepo;
    @Mock
    private BookingRepo bookingRepo;
    @InjectMocks
    private CustomerServiceImpl customerService;

    @Autowired
    IntegrationsProperties properties;

   /* @Autowired
    public CustomerServiceImplTests(CustomerRepo customerRepo, BookingRepo bookingRepo) {
        this.customerRepo = customerRepo;
        this.bookingRepo = bookingRepo;
        this.customerService = new CustomerServiceImpl(customerRepo, bookingRepo);
    } */

    @Mock
    CustomerServiceImpl service3;

    private Customer testcustomer;
    private Customer testCustomer1;
    private Customer testCustomer2;
    private CustomerDTO testCustomerDTO;

    @BeforeEach
    void setUp(){
        customerService = new CustomerServiceImpl(customerRepo,bookingRepo);
        testcustomer = new Customer((long) 123, "CN101", "Kalle",
                "012-345678", "abc@abcdef.se");
        testCustomer1 = new Customer((long) 124, "CN102", "Lisa",
                "012-666666", "abc@example.se");
        testCustomer2 = new Customer((long) 125, "CN103", "Märta",
                "012-777777", "test@example.se");
        testCustomerDTO = new CustomerDTO((long)222,"CN222","Domino",
                "012-888888","dtomail@mail.ml");
    }
    @Test
    void generateCustomerNr() {
        when(customerRepo.findAll()).thenReturn(Arrays.asList(testcustomer));
        CustomerServiceImpl service2 = new CustomerServiceImpl(customerRepo, bookingRepo);
        String testCustomerNr = service2.generateCustomerNr();
        assertEquals("CN102", testCustomerNr);
    }

    @Test
    void customerToCustomerDTO() {
        CustomerDTO customerDTO = customerService.customerToCustomerDTO(testcustomer);

        assertEquals(testcustomer.getId(), customerDTO.getId());
        assertEquals(testcustomer.getCustomerNumber(), customerDTO.getCustomerNumber());
        assertEquals(testcustomer.getCustomerName(), customerDTO.getCustomerName());
        assertEquals(testcustomer.getPhoneNumber(), customerDTO.getPhoneNumber());
        assertEquals(testcustomer.getEmail(), customerDTO.getEmail());
    }

    @Test
    void customerDTOToCustomerTest(){
        Customer resultCustomer = customerService.customerDTOToCustomer(testCustomerDTO);
        assertEquals(testCustomerDTO.getId(), resultCustomer.getId());
        assertEquals(testCustomerDTO.getCustomerNumber(), resultCustomer.getCustomerNumber());
        assertEquals(testCustomerDTO.getCustomerName(), resultCustomer.getCustomerName());
        assertEquals(testCustomerDTO.getPhoneNumber(), resultCustomer.getPhoneNumber());
        assertEquals(testCustomerDTO.getEmail(), resultCustomer.getEmail());
    }

    @Test
    void getAllCustomers() {
        when(service3.getAllCustomers()).thenReturn(Arrays.asList(customerService.customerToCustomerDTO(testcustomer),
                customerService.customerToCustomerDTO(testCustomer1)));
        List<CustomerDTO> customers = service3.getAllCustomers();

        assertEquals(2, customers.size());
        assertEquals(124, customers.get(1).getId());
        assertEquals("CN102", customers.get(1).getCustomerNumber());
        assertEquals("Lisa", customers.get(1).getCustomerName());
        assertEquals("012-666666", customers.get(1).getPhoneNumber());
        assertEquals("abc@example.se", customers.get(1).getEmail());

        assertEquals(123, customers.get(0).getId());
        assertEquals("CN101", customers.get(0).getCustomerNumber());
        assertEquals("Kalle", customers.get(0).getCustomerName());
        assertEquals("012-345678", customers.get(0).getPhoneNumber());
        assertEquals("abc@abcdef.se", customers.get(0).getEmail());
    }

    @Test
    void existsCustomerByEmail() {
        String email = testcustomer.getEmail();
        when(customerRepo.existsByEmail(email)).thenReturn(true);

        boolean exists = customerRepo.existsByEmail(email);

        assertTrue(exists);
    }

    @Test
    void checkIfCustomerHasBookings() {
        Long id = testcustomer.getId();
        when(bookingRepo.existsByCustomerId(id)).thenReturn(true);

        boolean exists = bookingRepo.existsByCustomerId(id);

        assertTrue(exists);
    }
}
package org.example.booking_project.service.impl;

import org.example.booking_project.Dtos.CustomerDTO;
import org.example.booking_project.models.Customer;
import org.example.booking_project.service.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Override
    public CustomerDTO customerToCustomerDTO(Customer c) {
        return CustomerDTO.builder().id(c.getId()).customerNumber(c.getCustomerNumber())
                .customerName(c.getCustomerName()).phoneNumber(c.getPhoneNumber()).email(c.getEmail()).build();
    }

    @Override
    public Customer customerDTOToCustomer(CustomerDTO c) {
        return Customer.builder().id(c.getId()).customerNumber(c.getCustomerNumber())
                .customerName(c.getCustomerName()).phoneNumber(c.getPhoneNumber()).email(c.getEmail()).build();
    }
}

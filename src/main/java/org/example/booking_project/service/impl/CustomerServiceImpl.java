package org.example.booking_project.service.impl;

import org.example.booking_project.Dtos.CustomerDTO;
import org.example.booking_project.models.Customer;
import org.example.booking_project.service.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Override
    public CustomerDTO customerToCustomerDTO(Customer c) {
        return null;
    }

    @Override
    public Customer customerDTOToCustomer(CustomerDTO c) {
        return null;
    }
}

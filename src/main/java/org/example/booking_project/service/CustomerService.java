package org.example.booking_project.service;

import org.example.booking_project.Dtos.CustomerDTO;
import org.example.booking_project.models.Customer;

public interface CustomerService {

    public CustomerDTO customerToCustomerDTO(Customer c);

    public Customer customerDTOToCustomer(CustomerDTO c);
}

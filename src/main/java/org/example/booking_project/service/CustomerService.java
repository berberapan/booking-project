package org.example.booking_project.service;

import org.example.booking_project.Dtos.CustomerDTO;
import org.example.booking_project.models.Customer;

import java.util.List;

public interface CustomerService {

    public CustomerDTO customerToCustomerDTO(Customer c);

    public Customer customerDTOToCustomer(CustomerDTO c);

    public List<CustomerDTO> getAllCustomers();

    public CustomerDTO getCustomerByEmail(String email);

    public void addCustomer(CustomerDTO customerDTO);

    public boolean existsCustomerByEmail(String email);

    public void updateCustomer(Long id, CustomerDTO customerDTO);

    public void deleteCustomer(Long id);

    public String generateCustomerNr();

}

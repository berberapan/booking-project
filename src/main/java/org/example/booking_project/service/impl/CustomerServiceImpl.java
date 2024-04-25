package org.example.booking_project.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.booking_project.Dtos.BookingDTO;
import org.example.booking_project.Dtos.CustomerDTO;
import org.example.booking_project.models.Customer;
import org.example.booking_project.repos.CustomerRepo;
import org.example.booking_project.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;

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

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepo.findAll().stream().map(this::customerToCustomerDTO).toList();
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = customerDTOToCustomer(customerDTO);
        Customer savedCustomer = customerRepo.save(customer);
        return customerToCustomerDTO(savedCustomer);
    }

    @Override
    public CustomerDTO getCustomerByEmail(String email) {
        Customer customer = customerRepo.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + email));
        return customerToCustomerDTO(customer);
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
        Customer updatedCustomer = customerDTOToCustomer(customerDTO);
        updatedCustomer.setId(existingCustomer.getId());
        Customer savedCustomer = customerRepo.save(updatedCustomer);
        return customerToCustomerDTO(savedCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer existingCustomer = customerRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
        //Hämta om kunden har några bokningar
    }
}

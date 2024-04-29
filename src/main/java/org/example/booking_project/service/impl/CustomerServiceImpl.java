package org.example.booking_project.service.impl;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.booking_project.Dtos.CustomerDTO;
import org.example.booking_project.controllers.BookingController;
import org.example.booking_project.models.Customer;

import lombok.RequiredArgsConstructor;
import org.example.booking_project.Dtos.CustomerDTO;
import org.example.booking_project.models.Customer;
import org.example.booking_project.repos.BookingRepo;

import org.example.booking_project.repos.CustomerRepo;
import org.example.booking_project.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.example.booking_project.service.impl.BookingServiceImpl.isNumeric;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private final CustomerRepo customerRepo;

    private final BookingRepo bookingRepo;


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
    public void addCustomer(CustomerDTO customerDTO) {
        log.info(customerDTO.getCustomerNumber());
        Customer customer = customerDTOToCustomer(customerDTO);
        log.info(customer.getCustomerNumber());
        Customer savedCustomer = customerRepo.save(customer);
        customerToCustomerDTO(savedCustomer);

    }
    /*
    @Override
    public void addCustomer2( String customerNumber, String customerName, String phoneNumber, String email) {
        Customer savedCustomer = customerRepo.save(new Customer( customerNumber, customerName, phoneNumber, email));
    }*/

    @Override
    public CustomerDTO getCustomerByEmail(String email) {
        Customer customer = customerRepo.findByEmail(email);
        return customerToCustomerDTO(customer);
    }

    @Override
    public boolean existsCustomerByEmail(String email) {
        return customerRepo.existsByEmail(email);
    }

    @Override
    public void updateCustomer(Long id, CustomerDTO customerDTO) {

        Customer existingCustomer = customerRepo.findById(id).orElse(null);
        if (existingCustomer != null) {
            existingCustomer.setCustomerName(customerDTO.getCustomerName());
            existingCustomer.setPhoneNumber(customerDTO.getPhoneNumber());
            existingCustomer.setEmail(customerDTO.getEmail());
            customerRepo.save(existingCustomer);
        }
    }

    @Override
    public void deleteCustomer(Long id) {

        Customer customer = customerRepo.findById(id).orElse(null);
        if (customer != null) {
            boolean hasBookings = bookingRepo.existsByCustomerId(id);
            if (!hasBookings) {
                customerRepo.deleteById(id);
            } else {
                System.out.println("Kunden har bokningar och kan inte tas bort!");
            }
        }
    }

    @Override
    public String generateCustomerNr() {
        int nr = 100;
        String abbr = "CN";

        for (Customer c : customerRepo.findAll()) {
            String customerNumber = c.getCustomerNumber();
            if (customerNumber != null) {
                String[] res = customerNumber.split("(?=\\d*$)", 2);
                if (res.length >= 2 && isNumeric(res[1])) {
                    int thisNr = Integer.parseInt(res[1]);
                    if (thisNr >= nr) {
                        nr = thisNr + 1;
                    }
                }
            }
        }
        return abbr + nr;
    }


}

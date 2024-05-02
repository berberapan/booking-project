package org.example.booking_project.service.impl;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.booking_project.Dtos.CustomerDTO;
import org.example.booking_project.models.Customer;

import org.example.booking_project.repos.BookingRepo;

import org.example.booking_project.repos.CustomerRepo;
import org.example.booking_project.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

import static org.example.booking_project.controllers.BookingController.handleConstraintViolationException;
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
    public void addCustomer(@Valid CustomerDTO customerDTO) {
        Customer customer = customerDTOToCustomer(customerDTO);
        Customer savedCustomer = customerRepo.save(customer);
        customerToCustomerDTO(savedCustomer);

    }

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
    public void updateCustomer(Long id, @Valid CustomerDTO customerDTO) {

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
                log.error("Kunden har bokningar och kan inte tas bort!");
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

    @Override
    public boolean checkIfCustomerHasBookings(Long id) {
        return bookingRepo.existsByCustomerId(id);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public String localExceptionHandler(ConstraintViolationException ex, Model model){
        return handleConstraintViolationException(ex,model);
    }
}

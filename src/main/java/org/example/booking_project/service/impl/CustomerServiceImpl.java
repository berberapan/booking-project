package org.example.booking_project.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.booking_project.Dtos.CustomerDTO;
import org.example.booking_project.models.Customer;
import org.example.booking_project.repos.CustomerRepo;
import org.example.booking_project.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.example.booking_project.service.impl.BookingServiceImpl.isNumeric;

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
    public void addCustomer(CustomerDTO customerDTO) {
        Customer customer = customerDTOToCustomer(customerDTO);
        Customer savedCustomer = customerRepo.save(customer);
        customerToCustomerDTO(savedCustomer);
    }

    @Override
    public CustomerDTO getCustomerByEmail(String email) {
        Customer customer = customerRepo.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Customer not found with email: " + email));
        return customerToCustomerDTO(customer);
    }

    @Override
    public void updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
        Customer updatedCustomer = customerDTOToCustomer(customerDTO);
        updatedCustomer.setId(existingCustomer.getId());
        customerRepo.save(updatedCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {
        // Hämta kundens bokningar
        // Är den tom så ta bort kunden
    }

    @Override
    public String generateCustomerNr() {
        int nr = 100;
        String abbr = "CN";
        String[] res;

        for (Customer c : customerRepo.findAll()) {
            res = c.getCustomerNumber().split("(?=\\d*$)", 2);
            if (isNumeric(res[1])) {
                int thisNr = Integer.parseInt(res[1]);
                if (thisNr >= nr) {
                    nr = thisNr + 1;
                }
            }
        }
        return abbr + nr;
    }
}

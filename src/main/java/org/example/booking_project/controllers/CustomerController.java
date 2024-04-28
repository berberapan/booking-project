package org.example.booking_project.controllers;

import lombok.RequiredArgsConstructor;
import org.example.booking_project.Dtos.CustomerDTO;
import org.example.booking_project.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("customers")
    List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

   /* Behövs inte?

   @PostMapping("customers/add")
    public String addCustomer(@RequestBody CustomerDTO customerDTO) {
        customerService.addCustomer(customerDTO);
        return "Customer created successfully";
    }*/

    @RequestMapping("customers/{email}")
    public CustomerDTO getCustomerByEmail(@PathVariable String email) {
        return customerService.getCustomerByEmail(email);
    }

    @PutMapping("customers/update/{id}")
    public String updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        customerService.updateCustomer(id, customerDTO);
        return "Customer updated successfully";
    }

    @DeleteMapping("customers/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "Customer deleted successfully";
    }
}

package org.example.booking_project.controllers;

import org.example.booking_project.Dtos.CustomerDTO;
import org.example.booking_project.service.CustomerService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CustomerController {

    private final CustomerService customerService;
      
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("customers")
    List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @RequestMapping("customers/{email}")
    public CustomerDTO getCustomerByEmail(@PathVariable String email) {
        return customerService.getCustomerByEmail(email);
    }

    @GetMapping("/customer/search")
    public String showSearchCustomerPage(Model model) {
        model.addAttribute("customer", new CustomerDTO());
        model.addAttribute("customerNotFound", false);
        model.addAttribute("updated", false);
        model.addAttribute("deleted", false);
        model.addAttribute("bookingExists", false);
        model.addAttribute("created", false);
        return "customer";
    }

    @PostMapping("/customer/search")
    public String searchCustomer(@RequestParam String email, Model model) {
        if (customerService.existsCustomerByEmail(email)) {
            CustomerDTO customerDTO = customerService.getCustomerByEmail(email);
            model.addAttribute("customer", customerDTO);
            model.addAttribute("customerNotFound", false);
        } else {
            model.addAttribute("customerNotFound", true);
        }
        return "customer";
    }

    @GetMapping("/customer/create")
    public String showCreateCustomerForm(Model model) {
        String generatedCustomerNr = customerService.generateCustomerNr();
        model.addAttribute("customer", new CustomerDTO());
        model.addAttribute("generatedCustomerNr", generatedCustomerNr);
        return "createCustomer";
    }

    @PostMapping("/customer/create")
    public String createOrUpdateCustomer(@ModelAttribute CustomerDTO customerDTO, Model model) {
        customerService.addCustomer(customerDTO);
        CustomerDTO updatedCustomer = customerService.getCustomerByEmail(customerDTO.getEmail());
        model.addAttribute("customer", updatedCustomer);
        model.addAttribute("created", true);
        return "createCustomer";
    }

    @PostMapping("/customer/update")
    public String updateCustomer(@ModelAttribute CustomerDTO customerDTO, Model model) {
        customerService.updateCustomer(customerDTO.getId(), customerDTO);
        model.addAttribute("updated", true);
        return "customer";
    }

    @GetMapping("/customer/delete")
    public String deleteCustomer(@RequestParam Long id, Model model) {
        boolean bookingExists = customerService.checkIfCustomerHasBookings(id);

        if (bookingExists) {
            model.addAttribute("bookingExists", true);
        } else {
            customerService.deleteCustomer(id);
            model.addAttribute("deleted", true);
        }

        return "customer";
    }
}


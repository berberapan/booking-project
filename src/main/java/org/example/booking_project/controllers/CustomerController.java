package org.example.booking_project.controllers;


import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.example.booking_project.Dtos.CustomerDTO;
import org.example.booking_project.service.CustomerService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.booking_project.controllers.BookingController.handleConstraintViolationException;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("customers")
    @PreAuthorize("isAuthenticated()")
    List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @RequestMapping("customers/{email}")
    @PreAuthorize("isAuthenticated()")
    public CustomerDTO getCustomerByEmail(@PathVariable String email) {
        return customerService.getCustomerByEmail(email);
    }

    @GetMapping("/customer/search")
    @PreAuthorize("isAuthenticated()")
    public String showSearchCustomerPage(Model model) {
        model.addAttribute("customer", new CustomerDTO());
        model.addAttribute("customerFormToggle", false);
        model.addAttribute("customerNotFound", false);
        model.addAttribute("updated", false);
        model.addAttribute("deleted", false);
        model.addAttribute("bookingExists", false);
        model.addAttribute("created", false);
        return "customer";
    }

    @PostMapping("/customer/search")
    @PreAuthorize("isAuthenticated()")
    public String searchCustomer(@RequestParam String email, Model model) {
        if (customerService.existsCustomerByEmail(email)) {
            CustomerDTO customerDTO = customerService.getCustomerByEmail(email);
            model.addAttribute("customer", customerDTO);
            model.addAttribute("customerNotFound", false);
            model.addAttribute("customerFormToggle", true);
        } else {
            model.addAttribute("customerNotFound", true);
            model.addAttribute("customerFormToggle", false);
        }
        return "customer";
    }

    @GetMapping("/customer/create")
    @PreAuthorize("isAuthenticated()")
    public String showCreateCustomerForm(Model model) {
        String generatedCustomerNr = customerService.generateCustomerNr();
        model.addAttribute("customer", new CustomerDTO());
        model.addAttribute("generatedCustomerNr", generatedCustomerNr);
        return "createCustomer";
    }

    @PostMapping("/customer/create")
    @PreAuthorize("isAuthenticated()")
    public String createCustomer(@ModelAttribute CustomerDTO customerDTO, Model model) {
        customerService.addCustomer(customerDTO);
        model.addAttribute("created", true);
        return "createCustomer";
    }

    @PostMapping("/customer/update")
    @PreAuthorize("isAuthenticated()")
    public String updateCustomer(@ModelAttribute CustomerDTO customerDTO, Model model) {
        customerService.updateCustomer(customerDTO.getId(), customerDTO);
        model.addAttribute("updated", true);
        return "customer";
    }

    @GetMapping("/customer/delete")
    @PreAuthorize("isAuthenticated()")
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

    @ExceptionHandler(ConstraintViolationException.class)
    public String localExceptionHandler(ConstraintViolationException ex, Model model){
        return handleConstraintViolationException(ex,model);
    }
}


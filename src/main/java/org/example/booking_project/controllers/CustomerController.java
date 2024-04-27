package org.example.booking_project.controllers;

import org.example.booking_project.Dtos.CustomerDTO;
import org.example.booking_project.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customer/search")
    public String showSearchCustomerPage(Model model) {
        model.addAttribute("customer", new CustomerDTO());
        return "customer";
    }

    @PostMapping("/customer/search")
    public String searchCustomer(@RequestParam String email, Model model) {
        if (customerService.existsCustomerByEmail(email)) {
            CustomerDTO customerDTO = customerService.getCustomerByEmail(email);
            model.addAttribute("customer", customerDTO);
        } else {
            model.addAttribute("customerNotFound", true);
        }
        return "customer";
    }

    @GetMapping("/customer/create")
    public String showCreateCustomerForm(Model model) {
        model.addAttribute("customer", new CustomerDTO());
        return "createCustomer";
    }

    @PostMapping("/customer/create")
    public String createOrUpdateCustomer(@ModelAttribute CustomerDTO customerDTO, Model model) {
        customerService.addCustomer(customerDTO);
        CustomerDTO updatedCustomer = customerService.getCustomerByEmail(customerDTO.getEmail());
        model.addAttribute("customer", updatedCustomer);
        return "redirect:/customer/search";
    }
    @PostMapping("/customer/update")
    public String updateCustomer(@ModelAttribute CustomerDTO customerDTO) {
        customerService.updateCustomer(customerDTO.getId(), customerDTO);
        return "redirect:/customer/search";
    }
    @GetMapping("/customer/delete")
    public String deleteCustomer(@RequestParam Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customer/search";
    }


}

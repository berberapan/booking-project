package org.example.booking_project.controllers;

import org.example.booking_project.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContractCustomerController {
    //Mock data! Byt till Contract customer service
    private final CustomerService customerService;

    public ContractCustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("contract-customer/search")
    public String showContractCustomers(Model model) {

        //Mock data! Byt till lista av contract customers DTO i modellen
        model.addAttribute("customers",customerService.getAllCustomers());
        return "contractCustomerTable";
    }
}

package org.example.booking_project.controllers;

import org.example.booking_project.models.Customer;
import org.example.booking_project.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ContractCustomerController {
    //Mock data! Byt till Contract customer service
    private final CustomerService customerService;

    public ContractCustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("contract-customer")
    public String showContractCustomers(Model model) {

        //Mock data! Byt till lista av contract customers DTO i modellen
        model.addAttribute("customers",customerService.getAllCustomers());
        return "contractCustomerTable";
    }

    @PostMapping("contract-customer/search")
    public String searchInContractCustomer(@RequestParam String search, Model model){

        //Mock data! Filtrerad lista ska in i modellen
        List<Customer> list = List.of();
        model.addAttribute("customers", list);
        return "contractCustomerTable";
    }
}

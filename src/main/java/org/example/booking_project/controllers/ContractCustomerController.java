package org.example.booking_project.controllers;

import org.example.booking_project.Dtos.ContractCustomerDTO;
import org.example.booking_project.models.ContractCustomer;
import org.example.booking_project.models.Customer;
import org.example.booking_project.service.ContractCustomerService;
import org.example.booking_project.service.CustomerService;
import org.example.booking_project.service.impl.ContractCustomerServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ContractCustomerController {
    //Mock data! Byt till Contract customer service
    private final CustomerService customerService;
    private final ContractCustomerService contractCustomerService;

    public ContractCustomerController(CustomerService customerService, ContractCustomerService contractCustomerService) {
        this.customerService = customerService;
        this.contractCustomerService = contractCustomerService;
    }

    @GetMapping("contractCustomer")
    public String showContractCustomers(Model model) {

        //Mock data! Byt till lista av contract customers DTO i modellen
        model.addAttribute("customers",customerService.getAllCustomers());
        return "contractCustomerTable.html";
    }

    @PostMapping("contractCustomer/search")
    public String searchInContractCustomer(@RequestParam String search, Model model){

        //Mock data! Filtrerad lista ska in i modellen
        List<Customer> list = List.of();
        model.addAttribute("customers", list);
        return "contractCustomerTable.html";
    }

    @GetMapping("contractCustomer/sort")
    public String sortContractCustomers(Model model){

        //Mock data! Filtrerad lista ska in i modellen
        List<Customer> list = List.of();
        model.addAttribute("customers", list);
        return "contractCustomerTable.html";
    }

    @GetMapping("contractCustomer/{customerId}")
    public String getCustomerDetails(Model model, @PathVariable Long customerId){
        ContractCustomer cc = contractCustomerService.getContractCustomerById(customerId);
        model.addAttribute("contractCustomer", cc);
        return "contractCustomerInfo";

    }
}

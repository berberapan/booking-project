package org.example.booking_project.controllers;

import org.example.booking_project.Dtos.ContractCustomerDTO;
import org.example.booking_project.service.ContractCustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ContractCustomerController {

    private final ContractCustomerService contractCustomerService;

    public ContractCustomerController(ContractCustomerService contractCustomerService) {
        this.contractCustomerService = contractCustomerService;
    }

    @GetMapping("contractCustomer")
    public String showContractCustomers(Model model) {
        List<ContractCustomerDTO> contractCustomerDTOS = contractCustomerService.getAllContractCustomers();
        model.addAttribute("customers", contractCustomerDTOS);
        return "contractCustomerTable";
    }

    /*

    @PostMapping("contractCustomer/search")
    public String searchInContractCustomer(@RequestParam String search, Model model){
        List<ContractCustomerDTO> filteredCustomers = contractCustomerService.searchContractCustomers(search);
        model.addAttribute("customers", filteredCustomers);
        return "contractCustomerTable";
    }

    @GetMapping("contractCustomer/sort")
    public String sortContractCustomers(@RequestParam String sortBy, Model model){
        List<ContractCustomerDTO> sortedCustomers = contractCustomerService.sortContractCustomers(sortBy);
        model.addAttribute("customers", sortedCustomers);
        return "contractCustomerTable";
    }

     */

    @GetMapping("/contractCustomer/{id}")
    public String showContractCustomerInfo(Model model, @PathVariable Long id) {
        ContractCustomerDTO customerDTO = contractCustomerService.getContractCustomerById(id);
        model.addAttribute("customer", customerDTO);
        return "contractCustomerInfo";
    }
}

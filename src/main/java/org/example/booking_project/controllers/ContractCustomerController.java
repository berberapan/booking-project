package org.example.booking_project.controllers;

import org.example.booking_project.Dtos.ContractCustomerDTO;
import org.example.booking_project.repos.ContractCustomerRepo;
import org.example.booking_project.service.ContractCustomerService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ContractCustomerController {

    private final ContractCustomerService contractCustomerService;
    private final ContractCustomerRepo contractCustomerRepo;

    public ContractCustomerController(ContractCustomerService contractCustomerService, ContractCustomerRepo contractCustomerRepo) {
        this.contractCustomerService = contractCustomerService;
        this.contractCustomerRepo = contractCustomerRepo;
    }

    @GetMapping("contractCustomer")
    public String showContractCustomers(Model model,
                                        @RequestParam(defaultValue = "ASC") String sortOrder,
                                        @RequestParam(defaultValue = "companyName") String sortCol,
                                        @RequestParam(defaultValue = "") String search) {

        search = search.strip();
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortCol);
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("sortCol", sortCol);
        model.addAttribute("search", search);
        /*
        List<ContractCustomerDTO> contractCustomerDTOS = contractCustomerService.getAllContractCustomers();
        model.addAttribute("customers", contractCustomerDTOS)
         */
        // model.addAttribute("customers", contractCustomerRepo.findAll(sort));
        model.addAttribute("customers", contractCustomerRepo.
                findAllByCompanyNameContainsOrContactNameContainsOrCountryContains(search, search, search, sort));
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

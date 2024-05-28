package org.example.booking_project.controllers;

import org.example.booking_project.Dtos.ContractCustomerDTO;
import org.example.booking_project.Dtos.ContractCustomerView;
import org.example.booking_project.models.ContractCustomer;
import org.example.booking_project.repos.ContractCustomerRepo;
import org.example.booking_project.service.ContractCustomerService;
import org.example.booking_project.service.impl.ContractCustomerServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final ContractCustomerServiceImpl contractCustomerServiceImpl;

    public ContractCustomerController(ContractCustomerService contractCustomerService, ContractCustomerRepo contractCustomerRepo, ContractCustomerServiceImpl contractCustomerServiceImpl) {
        this.contractCustomerService = contractCustomerService;
        this.contractCustomerRepo = contractCustomerRepo;
        this.contractCustomerServiceImpl = contractCustomerServiceImpl;
    }

    @GetMapping("contractCustomer")
    @PreAuthorize("isAuthenticated()")
    public String showContractCustomers(Model model,
                                        @RequestParam(defaultValue = "1") int pageNum,
                                        @RequestParam(defaultValue = "15") int pageSize,
                                        @RequestParam(defaultValue = "ASC") String sortOrder,
                                        @RequestParam(defaultValue = "companyName") String sortCol,
                                        @RequestParam(defaultValue = "") String search) {

        search = search.strip();
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortCol);
        Pageable pageable = PageRequest.of(pageNum-1, pageSize, sort);
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("sortCol", sortCol);
        model.addAttribute("search", search);

        if(!search.isEmpty()) {
            Page<ContractCustomerView> contractCustomers = contractCustomerServiceImpl.getFilteredContractCustomers(search, search, search, pageable);

            model.addAttribute("customers", contractCustomers);
            model.addAttribute("totalPages", contractCustomers.getTotalPages());
            model.addAttribute("pageNum", pageNum);
        } else {
            Page<ContractCustomerView> contractCustomers = contractCustomerServiceImpl.getAllContractCustomers(pageable);

            model.addAttribute("customers", contractCustomers);
            model.addAttribute("totalPages", contractCustomers.getTotalPages());
            model.addAttribute("pageNum", pageNum);
        }

        return "contractCustomerTable";
    }

    @GetMapping("/contractCustomer/{id}")
    @PreAuthorize("isAuthenticated()")
    public String showContractCustomerInfo(Model model, @PathVariable Long id) {
        ContractCustomerDTO customerDTO = contractCustomerService.getContractCustomerById(id);
        model.addAttribute("customer", customerDTO);
        return "contractCustomerInfo";
    }
}

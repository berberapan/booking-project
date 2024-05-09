package org.example.booking_project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContractCustomerController {

    @GetMapping("contract-customer/search")
    public String showContractCustomers(Model model) {
  //Lägg till lista av contract customers DTO i modellen
        return "contractCustomerTable";
    }
}

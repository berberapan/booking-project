package org.example.booking_project.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.booking_project.models.AllCustomers;
import org.example.booking_project.repos.ContractCustomerRepo;
import org.example.booking_project.service.ContractCustomerService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContractCustomerServiceImpl implements ContractCustomerService {

    private final ContractCustomerRepo contractCustomerRepo;

    @Override
    public void saveAllCustomers(AllCustomers allCustomers) {
        contractCustomerRepo.saveAll(allCustomers.getCustomers());
    }
}

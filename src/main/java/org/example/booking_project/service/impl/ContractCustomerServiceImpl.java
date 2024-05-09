package org.example.booking_project.service.impl;

import org.example.booking_project.Dtos.ContractCustomerDTO;
import org.example.booking_project.models.ContractCustomer;
import org.example.booking_project.repos.ContractCustomerRepo;
import org.example.booking_project.service.ContractCustomerService;
import org.springframework.stereotype.Service;

@Service
public class ContractCustomerServiceImpl implements ContractCustomerService {

    private final ContractCustomerRepo contractCustomerRepo;

    public ContractCustomerServiceImpl(ContractCustomerRepo contractCustomerRepo) {
        this.contractCustomerRepo = contractCustomerRepo;
    }


    @Override
    public ContractCustomerDTO contractCustomerToContractCustomerDTO(ContractCustomer c) {
        return ContractCustomerDTO.builder().id(c.getId()).companyName(c.getCompanyName()).contactName(c.getContactName())
                .contactTitle(c.getContactTitle()).streetAddress(c.getStreetAddress()).city(c.getCity())
                .postalCode(c.getPostalCode()).country(c.getCountry()).phone(c.getPhone()).fax(c.getFax())
                .build();
    }

    @Override
    public ContractCustomer contractCustomerDTOToContractCustomer(ContractCustomerDTO c) {
        return ContractCustomer.builder().id(c.getId()).companyName(c.getCompanyName()).contactName(c.getContactName())
                .contactTitle(c.getContactTitle()).streetAddress(c.getStreetAddress()).city(c.getCity())
                .postalCode(c.getPostalCode()).country(c.getCountry()).phone(c.getPhone()).fax(c.getFax())
                .build();
    }

    @Override
    public void updateOrAddContractCustomer(Long id, ContractCustomerDTO contractCustomerDTO) {
        ContractCustomer existingCustomer = contractCustomerRepo.findById(id).orElse(null);
        if (existingCustomer != null) {
            existingCustomer.setId(contractCustomerDTO.getId());
            existingCustomer.setCompanyName(contractCustomerDTO.getCompanyName());
            existingCustomer.setContactName(contractCustomerDTO.getContactName());
            existingCustomer.setContactTitle(contractCustomerDTO.getContactTitle());
            existingCustomer.setStreetAddress(contractCustomerDTO.getStreetAddress());
            existingCustomer.setCity(contractCustomerDTO.getCity());
            existingCustomer.setPostalCode(contractCustomerDTO.getPostalCode());
            existingCustomer.setCountry(contractCustomerDTO.getCountry());
            existingCustomer.setPhone(contractCustomerDTO.getPhone());
            existingCustomer.setFax(contractCustomerDTO.getFax());
            contractCustomerRepo.save(existingCustomer);
            System.out.println(existingCustomer.getCompanyName() +" updated");
        } else {
            ContractCustomer newCustomer = contractCustomerDTOToContractCustomer(contractCustomerDTO);
            contractCustomerRepo.save(newCustomer);
            System.out.println(newCustomer.getCompanyName() +" added");
        }
    }
}

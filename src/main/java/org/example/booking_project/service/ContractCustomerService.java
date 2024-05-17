package org.example.booking_project.service;

import org.example.booking_project.Dtos.ContractCustomerDTO;
import org.example.booking_project.models.ContractCustomer;

import java.io.IOException;
import java.util.List;

public interface ContractCustomerService {

    public ContractCustomerDTO contractCustomerToContractCustomerDTO(ContractCustomer c);

    public ContractCustomer contractCustomerDTOToContractCustomer(ContractCustomerDTO c);

    public void updateOrAddContractCustomer(Long id, ContractCustomerDTO contractCustomerDTO);

    public ContractCustomerDTO getContractCustomerById(Long customerId);

    public List<ContractCustomerDTO> getAllContractCustomers();

    public List<ContractCustomerDTO> getContractCustomers() throws IOException;

}


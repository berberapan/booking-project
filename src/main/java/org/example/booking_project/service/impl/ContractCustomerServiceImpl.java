package org.example.booking_project.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.Getter;
import org.example.booking_project.Dtos.ContractCustomerDTO;
import org.example.booking_project.Dtos.ContractCustomerView;
import org.example.booking_project.models.AllCustomers;
import org.example.booking_project.models.ContractCustomer;
import org.example.booking_project.repos.ContractCustomerRepo;
import org.example.booking_project.service.ContractCustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Getter
public class ContractCustomerServiceImpl implements ContractCustomerService {

    private final ContractCustomerRepo contractCustomerRepo;
    private final XmlStreamProvider xmlStreamProvider;
    private static final Logger log = LoggerFactory.getLogger(ContractCustomerServiceImpl.class);

    @Autowired
    ContractCustomerServiceImpl(ContractCustomerRepo contractCustomerRepo, XmlStreamProvider xmlStreamProvider){
        this.contractCustomerRepo = contractCustomerRepo;
        this.xmlStreamProvider = xmlStreamProvider;
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
            log.info("{} updated", existingCustomer.getCompanyName());
        } else {
            ContractCustomer newCustomer = contractCustomerDTOToContractCustomer(contractCustomerDTO);
            contractCustomerRepo.save(newCustomer);
            log.info("{} added", newCustomer.getCompanyName());
        }
    }

    @Override
    public ContractCustomerDTO getContractCustomerById(Long customerId) {
        ContractCustomer cc = contractCustomerRepo.findById(customerId).orElse(null);
        return contractCustomerToContractCustomerDTO(cc);
    }

    @Override
    public List<ContractCustomerDTO> getAllContractCustomers() {
        List<ContractCustomer> contractCustomers = contractCustomerRepo.findAll();
        return contractCustomers.stream()
                .map(this::contractCustomerToContractCustomerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContractCustomerDTO> getContractCustomers() throws IOException {
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);
        try (InputStream stream = xmlStreamProvider.getDataStream()) {
            AllCustomers customers = xmlMapper.readValue(stream, AllCustomers.class);
            return customers.customers.stream()
                    .map(this::contractCustomerToContractCustomerDTO)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Page<ContractCustomerView> getFilteredContractCustomers (String companyName, String contactName, String country, Pageable pageable) {
        Page<ContractCustomer> pages = contractCustomerRepo.findAllByCompanyNameContainsOrContactNameContainsOrCountryContains(
                companyName, contactName, country, pageable);
        return pages.map(customer -> new ContractCustomerView(
                customer.getId(),
                customer.getCompanyName(),
                customer.getContactName(),
                customer.getCountry()
        ));
    }

    @Override
    public Page<ContractCustomerView> getAllContractCustomers (Pageable pageable) {
        Page<ContractCustomer> pages = contractCustomerRepo.findAll(pageable);
        return pages.map(customer -> new ContractCustomerView(
                customer.getId(),
                customer.getCompanyName(),
                customer.getContactName(),
                customer.getCountry()
        ));
    }
}
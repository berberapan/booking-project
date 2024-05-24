package org.example.booking_project.repos;

import org.example.booking_project.models.ContractCustomer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ContractCustomerRepo extends JpaRepository<ContractCustomer, Long> {

    Page<ContractCustomer> findAllByCompanyNameContainsOrContactNameContainsOrCountryContains(String companyName,
                                                                                              String contactName,
                                                                                              String country, Pageable pageable);

    Page<ContractCustomer> findAll(Pageable pageable);
}

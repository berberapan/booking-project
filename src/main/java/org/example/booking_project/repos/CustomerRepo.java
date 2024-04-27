package org.example.booking_project.repos;

import org.example.booking_project.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

    Customer findByEmail(String email);

    boolean existsByEmail(String email);
}

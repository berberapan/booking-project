package org.example.booking_project.repos;

import org.example.booking_project.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

    Customer findByEmail(String email);

    boolean existsByEmail(String email);
}

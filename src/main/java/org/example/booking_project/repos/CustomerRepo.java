package org.example.booking_project.repos;

import org.example.booking_project.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

    public Customer findByEmail(String email);

    public boolean existsByEmail(String email);
}

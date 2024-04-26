package org.example.booking_project.repos;

import org.example.booking_project.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepo extends JpaRepository<Booking, Long> {

    public boolean existsByCustomerId(Long id);

}

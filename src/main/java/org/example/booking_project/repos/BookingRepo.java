package org.example.booking_project.repos;

import org.example.booking_project.models.Booking;
import org.example.booking_project.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Long> {

    public boolean existsByCustomerId(Long id);

    boolean existsByBookingNr(String bookingNr);
    Booking findByBookingNr(String bookingNr);
}

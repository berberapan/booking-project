package org.example.booking_project.service;

import org.example.booking_project.models.Booking;
import org.example.booking_project.repos.BookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    @Autowired
    BookingRepo bookingRepo;


    public Booking createBooking(Booking booking) {
        return bookingRepo.save(booking);
    }

    public void cancelBooking(Long bookingId){
        bookingRepo.deleteById(bookingId);
    }

}

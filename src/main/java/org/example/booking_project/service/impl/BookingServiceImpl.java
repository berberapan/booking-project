package org.example.booking_project.service.impl;

import org.example.booking_project.Dtos.BookingDTO;
import org.example.booking_project.models.Booking;
import org.example.booking_project.repos.BookingRepo;
import org.example.booking_project.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService {


    @Override
    public BookingDTO bookingToBookingDTO(Booking b) {
        return null;
    }

    @Override
    public Booking bookingDTOToBooking(BookingDTO b) {
        return null;
    }
}

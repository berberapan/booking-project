package org.example.booking_project.service;

import org.example.booking_project.Dtos.BookingDTO;
import org.example.booking_project.models.Booking;

public interface BookingService {

    public BookingDTO bookingToBookingDTO(Booking b);

    public Booking bookingDTOToBooking(BookingDTO b);

    public double calculatePrice(BookingDTO b);


}

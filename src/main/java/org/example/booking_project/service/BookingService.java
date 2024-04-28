package org.example.booking_project.service;

import org.example.booking_project.Dtos.BookingDTO;
import org.example.booking_project.models.Booking;
import org.example.booking_project.models.Customer;
import org.example.booking_project.models.Room;

public interface BookingService {

    public BookingDTO bookingToBookingDTO(Booking b);

    public Booking bookingDTOToBooking(BookingDTO b, Customer c, Room r);

    public double calculatePrice(BookingDTO b);

    public String generateBookingNr();


}

package org.example.booking_project.service;

import org.example.booking_project.Dtos.BookingDTO;
import org.example.booking_project.models.Booking;
import org.example.booking_project.models.Customer;
import org.example.booking_project.models.Room;

import java.time.LocalDate;

public interface BookingService {

    public BookingDTO bookingToBookingDTO(Booking b);

    public Booking bookingDTOToBooking(BookingDTO b, Customer c, Room r);

    public double calculatePrice(BookingDTO b);

    public Booking addBooking(Customer customer, Room room, int bookedBeds, LocalDate checkInDate, LocalDate checkOutDate);

}

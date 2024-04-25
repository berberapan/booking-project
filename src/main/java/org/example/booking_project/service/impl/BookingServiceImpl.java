package org.example.booking_project.service.impl;

import org.example.booking_project.Dtos.BookingDTO;
import org.example.booking_project.Dtos.CustomerDTO;
import org.example.booking_project.Dtos.RoomDTO;
import org.example.booking_project.models.Booking;
import org.example.booking_project.models.Customer;
import org.example.booking_project.models.Room;
import org.example.booking_project.service.BookingService;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

@Service
public class BookingServiceImpl implements BookingService {

    @Override
    public BookingDTO bookingToBookingDTO(Booking b) {
        return BookingDTO.builder().id(b.getId()).bookingNr(b.getBookingNr())
                .customer(new CustomerDTO(b.getCustomer().getId(), b.getCustomer().getCustomerNumber(), b.getCustomer()
                        .getCustomerName(), b.getCustomer().getPhoneNumber(), b.getCustomer().getEmail()))
                .room(new RoomDTO(b.getRoom().getId(), b.getRoom().getRoomNumber(), b.getRoom().getMaxBeds(),
                        b.getRoom().getPricePerNight(), b.getRoom().getRoomType()))
                .bookedBeds(b.getBookedBeds()).checkInDate(b.getCheckInDate()).checkOutDate(b.getCheckOutDate()).build();
    }

    @Override
    public Booking bookingDTOToBooking(BookingDTO b, Customer customer, Room room) {
        return Booking.builder().id(b.getId()).bookingNr(b.getBookingNr()).customer(customer).room(room)
                .bookedBeds(b.getBookedBeds()).checkInDate(b.getCheckInDate()).checkOutDate(b.getCheckOutDate()).build();
    }

    @Override
    public double calculatePrice(BookingDTO b) {
        return ChronoUnit.DAYS.between(b.getCheckInDate(), b.getCheckOutDate()) * b.getRoom().getPricePerNight();
    }
}

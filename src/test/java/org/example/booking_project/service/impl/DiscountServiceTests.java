package org.example.booking_project.service.impl;

import org.example.booking_project.Dtos.CustomerDTO;
import org.example.booking_project.models.Booking;
import org.example.booking_project.models.Customer;
import org.example.booking_project.models.Room;
import org.example.booking_project.models.RoomType;
import org.example.booking_project.repos.BookingRepo;
import org.example.booking_project.repos.CustomerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DiscountServiceTests {

    BookingRepo bookingRepo = mock(BookingRepo.class);
    CustomerRepo customerRepo = mock(CustomerRepo.class);
    RoomServiceImpl roomServiceImpl = mock(RoomServiceImpl.class);
    CustomerServiceImpl customerServiceImpl = mock(CustomerServiceImpl.class);
    Customer test;
    CustomerDTO testDTO;
    Room testroom;
    BookingServiceImpl sut;

    @BeforeEach
    void setUp() {
        sut = new BookingServiceImpl(bookingRepo, customerRepo, roomServiceImpl, customerServiceImpl);
        CustomerDTO testDTO = new CustomerDTO(2L, "CN002", "Test Testsson", "123456789", "test@test.com");
        Customer test = new Customer(2L, "CN002", "Test Testsson", "123456789", "test@test.com");
        when(customerServiceImpl.customerDTOToCustomer(testDTO)).thenReturn(test);
        Room testroom = new Room(321L, 101, RoomType.DOUBLE, 3, 500);
    }

    @Test
    void returnAmountOfDaysBookedOverLastYear() {
        Booking booking1 = new Booking("BN101", test, testroom, 2, LocalDate.now().minusMonths(6).minusDays(2), LocalDate.now().minusMonths(6));
        Booking booking2 = new Booking("BN100", test, testroom, 2, LocalDate.now().minusYears(1).minusDays(5), LocalDate.now().minusYears(1).minusDays(4));

        List<Booking> bookings = List.of(booking1, booking2);
       // List<Booking> bookingsCurrentYear = bookings.stream().filter(b -> b.getCheckInDate().isAfter(LocalDate.now().minusYears(1).minusDays(1))).toList();

        when(bookingRepo.findAllByCustomerAndCheckInDateAfter(eq(test), eq(LocalDate.now().minusYears(1).minusDays(1)))).thenReturn(bookings);

        System.out.println(booking2.getCheckInDate());

        assertEquals(2, sut.bookedNightsLastYear(testDTO));
    }
}
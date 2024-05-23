package org.example.booking_project.service.impl;

import org.example.booking_project.Dtos.BookingDTO;
import org.example.booking_project.Dtos.CustomerDTO;
import org.example.booking_project.Dtos.RoomDTO;
import org.example.booking_project.configs.IntegrationsProperties;
import org.example.booking_project.models.Booking;
import org.example.booking_project.models.Customer;
import org.example.booking_project.models.Room;
import org.example.booking_project.models.RoomType;
import org.example.booking_project.repos.BookingRepo;
import org.example.booking_project.repos.CustomerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
class DiscountServiceTests {

    BookingRepo bookingRepo = mock(BookingRepo.class);
    CustomerRepo customerRepo = mock(CustomerRepo.class);
    RoomServiceImpl roomServiceImpl = mock(RoomServiceImpl.class);
    CustomerServiceImpl customerServiceImpl = mock(CustomerServiceImpl.class);
    Customer test;
    CustomerDTO testDTO;
    Room testroom;
    RoomDTO testroomDTO;
    BookingServiceImpl sut;

    @Autowired
    IntegrationsProperties properties;

    @BeforeEach
    void setUp() {
        sut = new BookingServiceImpl(bookingRepo, customerRepo, roomServiceImpl, customerServiceImpl,properties);
        testDTO = new CustomerDTO(2L, "CN002", "Test Testsson", "123456789", "test@test.com");
        test = new Customer(2L, "CN002", "Test Testsson", "123456789", "test@test.com");
        when(customerServiceImpl.customerDTOToCustomer(testDTO)).thenReturn(test);
        testroom = new Room(321L, 101, RoomType.DOUBLE, 3, 500);
        testroomDTO = new RoomDTO( 321L, 101, 3, 500, RoomType.DOUBLE);
    }

    @Test
    void returnAmountOfDaysBookedOverLastYear() {
        Booking booking1 = new Booking("BN101", test, testroom, 2, LocalDate.now().minusMonths(6).minusDays(2), LocalDate.now().minusMonths(6));
        Booking booking2 = new Booking("BN100", test, testroom, 2, LocalDate.now().minusYears(1).minusDays(5), LocalDate.now().minusYears(1).minusDays(4));

        List<Booking> bookings = List.of(booking1, booking2);

        when(bookingRepo.findAllByCustomerAndCheckInDateAfter(eq(test), eq(LocalDate.now().minusYears(1).minusDays(1)))).thenReturn(
                bookings.stream().filter(b -> b.getCheckInDate().isAfter(LocalDate.now().minusYears(1).minusDays(1))).toList());

        assertEquals(2, sut.bookedNightsLastYear(testDTO));
    }

    @Test
    void bookingShouldHaveNoDiscount() {
        // Onsdag till torsdag - ingen rabatt
        BookingDTO booking = new BookingDTO(null,"BN100", testDTO, testroomDTO, 2, LocalDate.parse("2024-05-15"), LocalDate.parse("2024-05-16"));

        assertEquals(500.0, sut.calculatePrice(booking));
    }

    @Test
    void bookingSundayNightDiscount() {
        // Söndag till måndag - 2% rabatt
        BookingDTO booking = new BookingDTO(null,"BN100", testDTO, testroomDTO, 2, LocalDate.parse("2024-05-19"), LocalDate.parse("2024-05-20"));

        assertEquals(490.00, sut.calculatePrice(booking));
    }

    @Test
    void bookingTwoPlusNightsNotIncludingSunday() {
        // Onsdag till lördag - 0.5% rabatt
        BookingDTO booking = new BookingDTO(null,"BN100", testDTO, testroomDTO, 2, LocalDate.parse("2024-05-15"), LocalDate.parse("2024-05-18"));

        assertEquals(1492.5, sut.calculatePrice(booking));
    }

    @Test
    void bookingTwoPlusNightsIncludingSunday() {
        // Lördag till tisdag - 2% för söndagsnatten, 0,5% på totalpriset
        BookingDTO booking = new BookingDTO(null,"BN100", testDTO, testroomDTO, 2, LocalDate.parse("2024-05-18"), LocalDate.parse("2024-05-21"));

        assertEquals(1482.55, sut.calculatePrice(booking));
    }

    @Test
    void bookingWithOverTenNightsDiscount() {
        // Onsdag till torsdag - 2% för över 10 nätter senaste året
        BookingDTO booking = new BookingDTO(null,"BN100", testDTO, testroomDTO, 2, LocalDate.parse("2024-05-15"), LocalDate.parse("2024-05-16"));
        Booking booking1 = new Booking("BN101", test, testroom, 2, LocalDate.now().minusMonths(6).minusDays(15), LocalDate.now().minusMonths(6));
        List<Booking> bookings = List.of(booking1);
        when(bookingRepo.findAllByCustomerAndCheckInDateAfter(eq(test), any(LocalDate.class))).thenReturn(bookings);

        assertEquals(490.0, sut.calculatePrice(booking));
    }

    @Test
    void bookingTwoPlusNightsNotIncludingSundayWithTenNightsDiscount() {
        // Onsdag till lördag - 0,5% rabatt för över 2 nätter, 2% rabatt för över 10 nätter senaste året
        BookingDTO booking = new BookingDTO(null,"BN100", testDTO, testroomDTO, 2, LocalDate.parse("2024-05-15"), LocalDate.parse("2024-05-18"));
        Booking booking1 = new Booking("BN101", test, testroom, 2, LocalDate.now().minusMonths(6).minusDays(15), LocalDate.now().minusMonths(6));
        List<Booking> bookings = List.of(booking1);
        when(bookingRepo.findAllByCustomerAndCheckInDateAfter(eq(test), any(LocalDate.class))).thenReturn(bookings);

        assertEquals(1462.5, sut.calculatePrice(booking));
    }

    @Test
    void bookingWithAllAvailableDiscounts() {
        // Lördag till tisdag - 2% för söndagsnatten, 0,5% för över 2 nätter, 2% för över 10 nätter senaste året
        BookingDTO booking = new BookingDTO(null,"BN100", testDTO, testroomDTO, 2, LocalDate.parse("2024-05-18"), LocalDate.parse("2024-05-21"));
        Booking booking1 = new Booking("BN101", test, testroom, 2, LocalDate.now().minusMonths(6).minusDays(15), LocalDate.now().minusMonths(6));
        List<Booking> bookings = List.of(booking1);
        when(bookingRepo.findAllByCustomerAndCheckInDateAfter(eq(test), any(LocalDate.class))).thenReturn(bookings);

        assertEquals(1452.75, sut.calculatePrice(booking));
    }

}
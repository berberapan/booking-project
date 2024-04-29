package org.example.booking_project.service.impl;

import org.example.booking_project.Dtos.BookingDTO;
import org.example.booking_project.Dtos.CustomerDTO;
import org.example.booking_project.Dtos.RoomDTO;
import org.example.booking_project.models.Booking;
import org.example.booking_project.models.Customer;
import org.example.booking_project.models.Room;
import org.example.booking_project.models.RoomType;
import org.example.booking_project.repos.BookingRepo;
import org.example.booking_project.service.BookingService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookingServiceImplTest {

    @Mock
    private BookingRepo bookingRepo;

    @InjectMocks
    private BookingServiceImpl bookingService = new BookingServiceImpl(bookingRepo);

    private Customer testcustomer = new Customer((long) 123, "CN101", "Kalle",
            "012-345678", "abc@abcdef.se");
    private CustomerDTO testcustomerDTO = new CustomerDTO((long) 123, "CN101", "Kalle",
            "012-345678", "abc@abcdef.se");
    private Room testroom = new Room((long) 321, 101, RoomType.DOUBLE, 3, 500);
    private RoomDTO testroomDTO = new RoomDTO((long) 321, 101, 3, 500, RoomType.DOUBLE);
    private LocalDate checkIn = LocalDate.parse("2024-10-01");
    private LocalDate checkOut1 = LocalDate.parse("2024-10-02");
    private LocalDate checkOut2 = LocalDate.parse("2024-10-08");

    private Booking testbooking1 = new Booking((long) 213, "BN101", testcustomer, testroom, 2, checkIn, checkOut1);
    private Booking testbooking2 = new Booking((long) 213, "BN101", testcustomer, testroom, 2, checkIn, checkOut2);

    private BookingDTO actualBookingDTO = new BookingDTO((long) 213, "BN101", testcustomerDTO, testroomDTO, 2, checkIn, checkOut1);

    private BookingDTO testbdto1 = bookingService.bookingToBookingDTO(testbooking1);
    private BookingDTO testbdto2 = bookingService.bookingToBookingDTO(testbooking2);

    @Test
    void calculatePrice() {

        double result1 = bookingService.calculatePrice(testbdto1);
        double result2 = bookingService.calculatePrice(testbdto2);

        assertEquals(500, result1);
        assertEquals(3500, result2);
    }

    @Test
    void bookingToBookingDTO() {
        assertEquals(actualBookingDTO, bookingService.bookingToBookingDTO(testbooking1));
    }

    @Test
    void bookingDTOToBooking() {
        assertEquals(testbooking1, bookingService.bookingDTOToBooking(actualBookingDTO, testcustomer, testroom));
    }

    @Test
    void generateBookingNr() {
        when(bookingRepo.findAll()).thenReturn(Arrays.asList(testbooking1));
        BookingServiceImpl service2 = new BookingServiceImpl(bookingRepo);
        String testBookingNr = service2.generateBookingNr();
        assertEquals("BN102", testBookingNr);
    }
}
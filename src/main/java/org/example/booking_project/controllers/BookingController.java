package org.example.booking_project.controllers;

import lombok.RequiredArgsConstructor;
import org.example.booking_project.Dtos.BookingDTO;
import org.example.booking_project.service.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @RequestMapping ("bookings")
    List<BookingDTO> getAllBookings(){
        return bookingService.getAllBookings();
    }

    @PostMapping("/bookings/add")
    public String addBooking(@RequestBody BookingDTO b){
        return bookingService.addBooking(b);
    }

    @DeleteMapping("/bookings/delete/{id}")
    public String deleteBooking(@PathVariable Long id){
        bookingService.deleteBooking(id);
        return "Removed bookings";
    }

}

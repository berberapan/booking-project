package org.example.booking_project.controllers;

import lombok.RequiredArgsConstructor;
import org.example.booking_project.Dtos.BookingDTO;
import org.example.booking_project.Dtos.CustomerDTO;
import org.example.booking_project.service.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }

    @RequestMapping ("bookings")
    List<BookingDTO> getAllBookings(){
        return bookingService.getAllBookings();
    }

    @PostMapping("/bookings/add")
    public String addBooking(@RequestBody BookingDTO b){
        return bookingService.addBooking(b);
    }

    @GetMapping("/bookings/delete")
    public String deleteBooking(@RequestParam Long id, Model model) {
        bookingService.deleteBooking(id);
        model.addAttribute("deleted", true);
        return "booking";
    }

    @PostMapping("/bookings/update")
    public String updateBooking(@ModelAttribute BookingDTO bookingDTO, Model model) {
        bookingService.updateBooking(bookingDTO.getId(), bookingDTO);
        model.addAttribute("updated", true);
        return "booking";
    }

    @PostMapping("/bookings/search")
    public String searchBooking(@RequestParam String bookingNr, Model model) {
        if (bookingService.existsBookingByBookingNr(bookingNr)) {
            BookingDTO bookingDTO = bookingService.getBookingByBookingNr(bookingNr);
            model.addAttribute("booking", bookingDTO);
            model.addAttribute("bookingNotFound", false);
        } else {
            model.addAttribute("bookingNotFound", true);
        }
        return "booking";
    }

    @GetMapping("/bookings/search")
    public String showSearchBookingPage(Model model) {
        model.addAttribute("booking", new BookingDTO());
        model.addAttribute("bookingNotFound", false);
        model.addAttribute("updated", false);
        model.addAttribute("deleted", false);
        model.addAttribute("created", false);
        return "booking";
    }



}

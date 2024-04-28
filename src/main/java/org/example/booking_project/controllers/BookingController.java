package org.example.booking_project.controllers;

import org.example.booking_project.Dtos.BookingDTO;
import org.example.booking_project.Dtos.CustomerDTO;
import org.example.booking_project.Dtos.RoomDTO;
import org.example.booking_project.models.Booking;
import org.example.booking_project.models.Room;
import org.example.booking_project.service.impl.BookingServiceImpl;
import org.example.booking_project.service.impl.CustomerServiceImpl;
import org.example.booking_project.service.impl.RoomServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;


@Controller
public class BookingController {

    private static final Logger log = LoggerFactory.getLogger(BookingController.class);

    private BookingServiceImpl bs;
    private RoomServiceImpl rs;
    private CustomerServiceImpl cs;

    public BookingController(RoomServiceImpl rs, CustomerServiceImpl cs, BookingServiceImpl bs) {
        this.rs = rs;
        this.cs = cs;
        this.bs = bs;
    }

    private void commonModels(Model model, String numGuests, String in, String out) {
        model.addAttribute("numGuests", numGuests);
        model.addAttribute("in", in);
        model.addAttribute("out", out);
    }

    @RequestMapping("/book")
    public String book(){
        return "searchAvailability.html";
    }

    @RequestMapping("bookReceival")
    public String bookReceival (@RequestParam String numGuests,
                                @RequestParam String in,
                                @RequestParam String out, Model model) {

        LocalDate inCheck = LocalDate.parse(in);
        LocalDate outCheck = LocalDate.parse(out);
        commonModels(model, numGuests, in, out);
        List<RoomDTO> listOfRooms = rs.availableRooms(inCheck, outCheck, Integer.parseInt(numGuests));
        model.addAttribute("listOfRooms", listOfRooms);

        return "searchAvailabilityResult.html";
    }

    @RequestMapping("createbooking")
    public String createBooking(@RequestParam String email,
                                @RequestParam String in,
                                @RequestParam String out,
                                @RequestParam String numGuests,
                                @RequestParam String roomNumber,
                                @RequestParam(required = false) String name,
                                @RequestParam(required = false) String phone, Model model) {

        LocalDate inCheck = LocalDate.parse(in);
        LocalDate outCheck = LocalDate.parse(out);
        commonModels(model, numGuests, in, out);
        model.addAttribute("email", email);
        model.addAttribute("roomNumber", roomNumber);
        model.addAttribute("name", name);
        List<RoomDTO> listOfRooms = rs.availableRooms(inCheck, outCheck, Integer.parseInt(numGuests));
        model.addAttribute("listOfRooms", listOfRooms);

        if (name != null && phone != null) {
            cs.addCustomer(name, phone, email);
        }
        CustomerDTO customerDTO = cs.getCustomerByEmail(email);
        if (customerDTO == null) {
            model.addAttribute("missingCustomer", true);
            return "searchAvailabilityResult.html";
        }
        log.info(roomNumber);

        Room r = rs.getRoom(Integer.parseInt(roomNumber));
        Booking booking = bs.addBooking(cs.customerDTOToCustomer(customerDTO),r, Integer.parseInt(numGuests), inCheck, outCheck);
        BookingDTO bdto = bs.bookingToBookingDTO(booking);
        model.addAttribute("totalPrice",bs.calculatePrice(bdto));
        log.info(String.valueOf(bs.calculatePrice(bdto)));

        return "bookingConfirmation.html";
    }
}

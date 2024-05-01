package org.example.booking_project.controllers;
import jakarta.validation.Valid;
import org.example.booking_project.Dtos.BookingDTO;
import org.example.booking_project.Dtos.CustomerDTO;
import org.example.booking_project.Dtos.MiniBookingDTO;
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
import org.example.booking_project.service.BookingService;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookingController {

    private static final Logger log = LoggerFactory.getLogger(BookingController.class);

    private final BookingServiceImpl bs;
    private final RoomServiceImpl rs;
    private final CustomerServiceImpl cs;

    public BookingController(RoomServiceImpl rs, CustomerServiceImpl cs, BookingServiceImpl bs, BookingService bookingService) {
        this.rs = rs;
        this.cs = cs;
        this.bs = bs;
    }

    @GetMapping("/bookings/delete")
    public String deleteBooking(@RequestParam Long id, Model model) {
        bs.deleteBooking(id);
        model.addAttribute("deleted", true);
        return "booking";
    }

    @PostMapping("/bookings/update")
    public String updateBooking(@Valid @ModelAttribute BookingDTO bookingDTO, Model model) {
        bs.updateBooking(bookingDTO.getId(), bookingDTO);
        model.addAttribute("updated", true);
        return "booking";
    }

    @PostMapping("/bookings/search")
    public String searchBooking(@RequestParam String bookingNr, Model model) {
        if (bs.existsBookingByBookingNr(bookingNr)) {
            BookingDTO bookingDTO = bs.getBookingByBookingNr(bookingNr);
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

    @RequestMapping("/book")
    public String book(Model model){
        model.addAttribute("booking", new MiniBookingDTO());
        return "searchAvailability.html";
    }

    @RequestMapping("bookReceival")
    public String bookReceival (@ModelAttribute MiniBookingDTO booking, Model model) {

        model.addAttribute("book", booking);
        List<RoomDTO> listOfRooms = rs.availableRooms(booking.getCheckInDate(),booking.getCheckOutDate(), booking.getBookedBeds());
        model.addAttribute("listOfRooms", listOfRooms);
        model.addAttribute("customer", new CustomerDTO());

        return "searchAvailabilityResult.html";
    }

    @RequestMapping("createbooking")
    public String createBooking(@ModelAttribute MiniBookingDTO booking,
                                @ModelAttribute CustomerDTO customer,
                                @RequestParam String roomNumber, Model model) {

        model.addAttribute("book",booking);
        model.addAttribute("roomNumber", roomNumber);
        List<RoomDTO> listOfRooms = rs.availableRooms(booking.getCheckInDate(),booking.getCheckOutDate(), booking.getBookedBeds());
        model.addAttribute("listOfRooms", listOfRooms);

        if(customer.getCustomerName() != null && customer.getPhoneNumber() != null){
            customer.setCustomerNumber(cs.generateCustomerNr());
            cs.addCustomer(customer);
        }

        if (cs.existsCustomerByEmail(customer.getEmail())) {
            BookingDTO bdto = bs.addBooking(customer, booking, roomNumber);
            model.addAttribute("booking",bdto);
            model.addAttribute("totalPrice",bs.calculatePrice(bdto));

            return "bookingConfirmation.html";
        }

        model.addAttribute("missingCustomer", true);

        return "searchAvailabilityResult.html";
    }
  
    @DeleteMapping("/bookings/delete/{id}")
    public String deleteBooking(@PathVariable Long id){
        bs.deleteBooking(id);
        return "Removed bookings";
    }
  
    @RequestMapping ("bookings")
    List<BookingDTO> getAllBookings(){
        return bs.getAllBookings();
    }
}

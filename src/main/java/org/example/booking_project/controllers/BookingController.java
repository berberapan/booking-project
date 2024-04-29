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

    private void commonModels(Model model, String numGuests, String in, String out) {
        model.addAttribute("numGuests", numGuests);
        model.addAttribute("in", in);
        model.addAttribute("out", out);
    }
  
    @GetMapping("/bookings/delete")
    public String deleteBooking(@RequestParam Long id, Model model) {
        bs.deleteBooking(id);
        model.addAttribute("deleted", true);
        return "booking";
    }

    @PostMapping("/bookings/update")
    public String updateBooking(@ModelAttribute BookingDTO bookingDTO, Model model) {
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
    public String createBooking(@RequestParam String in,
                                @RequestParam String out,
                                @RequestParam String numGuests,
                                @RequestParam String roomNumber,
                                @RequestParam String email,
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

        if(name != null && phone !=null){
            String customerNr = cs.generateCustomerNr();
            CustomerDTO customerDTO = new CustomerDTO(null, customerNr, name, phone, email);
            log.info(customerDTO.getCustomerNumber());
            cs.addCustomer(customerDTO);
        }

        if (cs.existsCustomerByEmail(email)) {
            CustomerDTO customerDTO = cs.getCustomerByEmail(email);
            Room r = rs.getRoom(Integer.parseInt(roomNumber));
            Booking booking = bs.addBooking(cs.customerDTOToCustomer(customerDTO),r, Integer.parseInt(numGuests), inCheck, outCheck);
            BookingDTO bdto = bs.bookingToBookingDTO(booking);
            model.addAttribute("totalPrice",bs.calculatePrice(bdto));
            log.info(String.valueOf(bs.calculatePrice(bdto)));

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

package org.example.booking_project.controllers;

import org.example.booking_project.Dtos.RoomDTO;
import org.example.booking_project.service.impl.RoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class BookingController {

    private RoomServiceImpl rs;

    public BookingController(RoomServiceImpl rs) {
        this.rs = rs;
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

        model.addAttribute("numGuests", numGuests);
        model.addAttribute("in", in);
        model.addAttribute("out", out);
        List<RoomDTO> lista = rs.availableRooms(inCheck, outCheck);
        model.addAttribute("lista", lista);

        return "searchAvailabilityResult.html";
    }

}

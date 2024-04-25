package org.example.booking_project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class BookingController {

    @RequestMapping("/book")
    public String book(){
        return "searchAvailability.html";
    }
    @RequestMapping("bookReceival")
    public String bookReceival (@RequestParam String numGuests,
                                @RequestParam String in,
                                @RequestParam String out, Model model) {
        model.addAttribute("numGuests", numGuests);
        model.addAttribute("in", in);
        model.addAttribute("out", out);

        return "searchAvailabilityResult.html";
    }

}

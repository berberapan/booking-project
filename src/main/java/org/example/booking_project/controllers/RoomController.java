package org.example.booking_project.controllers;

import org.example.booking_project.Dtos.EventBaseDTO;
import org.example.booking_project.Dtos.RoomDTO;
import org.example.booking_project.repos.EventRepo;
import org.example.booking_project.service.RoomService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RoomController {

    RoomService roomService;
    EventRepo eventRepo;
    private static final Logger log = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    public RoomController(RoomService roomService, EventRepo eventRepo) {
        this.roomService = roomService;
        this.eventRepo = eventRepo;
    }

    @GetMapping("rooms")
    @PreAuthorize("isAuthenticated()")
    public String getAllRooms(Model model) {
        List<RoomDTO> rooms = roomService.allRooms();
        model.addAttribute("rooms", rooms);
        return "allRooms";
    }

    @GetMapping("events/{roomNumber}")
    @PreAuthorize("isAuthenticated()")
    public String getEvents(@PathVariable String roomNumber, Model model) {

        EventBaseDTO[] events = new ModelMapper().map(eventRepo.findAll(), EventBaseDTO[].class);

        List<EventBaseDTO> filteredEvents = Arrays.stream(events)
                .filter(event -> roomNumber.equals(event.getRoomNo()))
                .toList();

        model.addAttribute( "roomNumber", roomNumber);
        model.addAttribute("events", filteredEvents);
        return "roomEvent";
    }
}

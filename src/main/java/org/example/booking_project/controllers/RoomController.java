package org.example.booking_project.controllers;

import org.example.booking_project.Dtos.RoomDTO;
import org.example.booking_project.models.EventBase;
import org.example.booking_project.repos.EventRepo;
import org.example.booking_project.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

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
    public String getAllRooms(Model model) {
        List<RoomDTO> rooms = roomService.allRooms();
        model.addAttribute("rooms", rooms);
        return "allRooms";
    }

    @GetMapping("events/{roomNumber}")
    public String getEvents(@PathVariable String roomNumber, Model model) {
        List<EventBase> events = eventRepo.findAll();
        model.addAttribute( "roomNumber", roomNumber);
        model.addAttribute("events", events);
        return "roomEvent";
    }
}

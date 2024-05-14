package org.example.booking_project.controllers;

import org.example.booking_project.Dtos.RoomDTO;
import org.example.booking_project.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RoomController {

    RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("rooms")
    public String getAllRooms(Model model) {
        List<RoomDTO> rooms = roomService.allRooms();
        model.addAttribute("rooms", rooms);
        return "allRooms";
    }

    @GetMapping("events")
    public String getEvents() {
        return "roomEvent";
    }
}

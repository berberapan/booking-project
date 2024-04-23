package org.example.booking_project.service;


import org.example.booking_project.models.Room;
import org.example.booking_project.repos.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    RoomRepo roomRepo;

    public List<Room> getAllRooms() {
        return roomRepo.findAll();
    }
}

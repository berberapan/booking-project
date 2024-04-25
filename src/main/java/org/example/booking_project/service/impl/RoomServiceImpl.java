package org.example.booking_project.service.impl;


import org.example.booking_project.Dtos.RoomDTO;
import org.example.booking_project.models.Room;
import org.example.booking_project.repos.RoomRepo;
import org.example.booking_project.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Override
    public RoomDTO roomToRoomDTO(Room r) {
        return null;
    }

    @Override
    public Room roomDTOToRoom(RoomDTO r) {
        return null;
    }
}

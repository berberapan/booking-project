package org.example.booking_project.service;

import org.example.booking_project.Dtos.RoomDTO;
import org.example.booking_project.models.Room;

import java.time.LocalDate;
import java.util.List;

public interface RoomService {


    public RoomDTO roomToRoomDTO(Room r);

    public Room roomDTOToRoom(RoomDTO r);

    public List<RoomDTO> availableRooms(LocalDate checkIn, LocalDate checkOut, int numGuest);
}

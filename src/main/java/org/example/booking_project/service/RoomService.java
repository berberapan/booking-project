package org.example.booking_project.service;

import org.example.booking_project.Dtos.RoomDTO;
import org.example.booking_project.models.Room;

public interface RoomService {

    public RoomDTO roomToRoomDTO(Room r);

    public Room roomDTOToRoom(RoomDTO r);

}

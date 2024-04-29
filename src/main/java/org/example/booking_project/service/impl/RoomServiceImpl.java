package org.example.booking_project.service.impl;


import org.example.booking_project.Dtos.RoomDTO;
import org.example.booking_project.models.Room;
import org.example.booking_project.service.RoomService;
import org.springframework.stereotype.Service;


@Service
public class RoomServiceImpl implements RoomService {

    @Override
    public RoomDTO roomToRoomDTO(Room r) {
        return RoomDTO.builder().id(r.getId()).roomNumber(r.getRoomNumber()).maxBeds(r.getMaxBeds())
                .pricePerNight(r.getPricePerNight()).roomType(r.getRoomType()).build();
    }

    @Override
    public Room roomDTOToRoom(RoomDTO r) {
        return Room.builder().id(r.getId()).roomNumber(r.getRoomNumber()).roomType(r.getRoomType())
                .maxBeds(r.getMaxBeds()).pricePerNight(r.getPricePerNight()).build();
    }
}

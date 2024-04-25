package org.example.booking_project.service.impl;


import lombok.RequiredArgsConstructor;
import org.example.booking_project.Dtos.RoomDTO;
import org.example.booking_project.models.Room;
import org.example.booking_project.repos.RoomRepo;
import org.example.booking_project.service.RoomService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class RoomServiceImpl implements RoomService {


    private final RoomRepo rr;

    public RoomServiceImpl(RoomRepo rr) {
        this.rr = rr;
    }

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

    public List<RoomDTO> availableRooms(LocalDate checkIn, LocalDate checkOut) {
        return rr.available(checkIn, checkOut).stream().map(this::roomToRoomDTO).toList();
    }

}

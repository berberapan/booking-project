package org.example.booking_project.service.impl;

import org.example.booking_project.Dtos.ContractCustomerDTO;
import org.example.booking_project.Dtos.RoomDTO;
import org.example.booking_project.models.ContractCustomer;
import org.example.booking_project.models.Room;
import org.example.booking_project.repos.RoomRepo;
import org.example.booking_project.service.RoomService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


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

    @Override
    public List<RoomDTO> availableRooms(LocalDate checkIn, LocalDate checkOut, int numGuest) {
        List<RoomDTO> all = allRooms();
        List<RoomDTO> booked = rr.notAvailable(checkIn, checkOut).stream().map(this::roomToRoomDTO).toList();
        return all.stream().filter(r -> !booked.contains(r) && numGuest <= r.getMaxBeds()).collect(Collectors.toList());
    }

    public List<RoomDTO> allRooms() {
        return rr.findAll().stream().map(this::roomToRoomDTO).toList();
    }

    public Room getRoom(int roomNr) {
        return rr.findAll().stream().filter(r -> r.getRoomNumber() == roomNr).findAny().get();
    }
}

package org.example.booking_project.service.impl;

import org.example.booking_project.Dtos.RoomDTO;
import org.example.booking_project.models.Room;
import org.example.booking_project.models.RoomType;
import org.example.booking_project.repos.RoomRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RoomServiceImplTests {
    @Mock
    RoomRepo roomRepo;
    Room testroom;
    Room testroom2;
    Room testroom3;
    RoomDTO testroomDTO;
    RoomDTO testroomDTO2;
    LocalDate checkin;
    LocalDate checkout;
    int numGuest;
    @InjectMocks
    private RoomServiceImpl roomService;
    @BeforeEach
    void setup(){
        roomService = new RoomServiceImpl(roomRepo);
        testroom = new Room((long) 321, 101, RoomType.DOUBLE, 3, 500);
        testroom2 = new Room((long) 322, 102, RoomType.SINGLE, 1, 200);
        testroom3 = new Room((long) 323, 103, RoomType.SINGLE, 1, 200);
        testroomDTO = new RoomDTO((long) 321, 101, 3, 500, RoomType.DOUBLE);
        testroomDTO2 = new RoomDTO((long) 322, 102, 1, 200, RoomType.SINGLE);
        checkin = LocalDate.now().plusMonths(1);
        checkout = LocalDate.now().plusMonths(1).plusDays(1);
        numGuest = 2;
    }

    @Test
    void roomToRoomDTOTest(){
        assertEquals(testroomDTO, roomService.roomToRoomDTO(testroom));
    }

    @Test
    void roomDTOToRoomTest(){
        assertEquals(testroom, roomService.roomDTOToRoom(testroomDTO));
    }
    @Test
    void availableRoomsTest(){
        when(roomRepo.notAvailable(checkin,checkout)).thenReturn(List.of(testroom3));
        when(roomRepo.findAll()).thenReturn(List.of(testroom,testroom2,testroom3));

        List <RoomDTO> resultList = roomService.availableRooms(checkin,checkout,numGuest);

        assertEquals(1,resultList.size());
        assertEquals(resultList.get(0),testroomDTO);

        numGuest = 1;
        List <RoomDTO> resultList2 = roomService.availableRooms(checkin,checkout, numGuest);

        assertEquals(2,resultList2.size());
        assertEquals(resultList2.get(0),testroomDTO);
        assertEquals(resultList2.get(1),testroomDTO2);

    }
    @Test
    void allRoomsShouldReturnListOfDTOs(){
    when(roomRepo.findAll()).thenReturn(List.of(testroom,testroom2));

    List<RoomDTO> resultList = roomService.allRooms();

        assertEquals(2, resultList.size());
        assertEquals(resultList.get(0), testroomDTO);
        assertEquals(resultList.get(1).getId(), testroom2.getId());
        assertEquals(resultList.get(1).getRoomNumber(), testroom2.getRoomNumber());
        assertEquals(resultList.get(1).getRoomType(), testroom2.getRoomType());
        assertEquals(resultList.get(1).getMaxBeds(), testroom2.getMaxBeds());
        assertEquals(resultList.get(1).getPricePerNight(), testroom2.getPricePerNight());
    }
    @Test
    void getRoomShouldReturnRoomWithMatchingRoomnumber(){
        when(roomRepo.findAll()).thenReturn(List.of(testroom,testroom2));

        Room resultRoom1 = roomService.getRoom(101);
        Room resultRoom2 = roomService.getRoom(102);

        assertEquals(testroom,resultRoom1);
        assertEquals(testroom2,resultRoom2);
    }
}

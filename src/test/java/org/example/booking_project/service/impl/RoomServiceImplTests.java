package org.example.booking_project.service.impl;

import org.example.booking_project.Dtos.RoomDTO;
import org.example.booking_project.models.Room;
import org.example.booking_project.models.RoomType;
import org.example.booking_project.repos.RoomRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RoomServiceImplTests {
    @Mock
    RoomRepo roomRepo;
    Room testroom;
    Room testroom2;
    RoomDTO testroomDTO;

    private RoomServiceImpl roomService;
    @BeforeEach
    void setup(){
        roomService = new RoomServiceImpl(roomRepo);
        testroom = new Room((long) 321, 101, RoomType.DOUBLE, 3, 500);
        testroom2 = new Room((long) 322, 102, RoomType.SINGLE, 1, 200);
        testroomDTO = new RoomDTO((long) 321, 101, 3, 500, RoomType.DOUBLE);
    }

    @Test
    void roomToRoomDTOTest(){

    }

}

package org.example.booking_project.repos;

import org.example.booking_project.Dtos.RoomDTO;
import org.example.booking_project.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepo extends JpaRepository<Room, Long> {


    @Query("select b from Booking b where (b.checkInDate <= :checkOut and b.checkOutDate > :checkIn)")
    public List<Room> available(LocalDate checkIn, LocalDate checkOut);

}

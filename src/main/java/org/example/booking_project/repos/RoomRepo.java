package org.example.booking_project.repos;

import org.example.booking_project.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;


@Repository
public interface RoomRepo extends JpaRepository<Room, Long> {


    @Query("select b.room from Booking b where (b.checkInDate <= :checkOut and b.checkOutDate > :checkIn)")
    public List<Room> notAvailable(LocalDate checkIn, LocalDate checkOut);

}

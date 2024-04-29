package org.example.booking_project.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.booking_project.models.Customer;
import org.example.booking_project.models.Room;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDTO {

    private Long id;

    private String bookingNr;

    private CustomerDTO customer;

    private RoomDTO room;

    private int bookedBeds;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;
}



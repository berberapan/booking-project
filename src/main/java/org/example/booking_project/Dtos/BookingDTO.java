package org.example.booking_project.Dtos;


import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    private Customer customer;

    private Room room;

    private int bookedBeds;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;
}



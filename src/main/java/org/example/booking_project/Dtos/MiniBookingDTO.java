package org.example.booking_project.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MiniBookingDTO {

    private int bookedBeds;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;
}


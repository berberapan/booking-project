package org.example.booking_project.Dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDTO {

    private Long id;

    @NotEmpty(message = "Bokningsnummer är obligatoriskt")
    @Size(min = 5, message = "Minst 5 tecken för bokningsnummer, BN följt av siffror: BN1***")
    private String bookingNr;

    @NotEmpty(message = "Kund är obligatorisk")
    private CustomerDTO customer;

    @NotEmpty(message = "Rum är obligatoriskt")
    private RoomDTO room;

    @NotEmpty(message = "Bokade sängar är obligatoriskt")
    @Min(value = 1, message = "Bokningen måste ha minst 1 bokad säng")
    @Max(value = 4, message = "Bokningen får inte ha fler än 4 bokade sängar")
    private int bookedBeds;

    @NotEmpty(message = "Incheckningsdatum är obligatoriskt")
    @FutureOrPresent(message = "Incheckningsdatum måste vara dagens datum eller ett kommande datum")
    private LocalDate checkInDate;

    @NotEmpty(message = "Utcheckningsdatum är obligatoriskt")
    @Future (message = "Utcheckningsdatum måste vara ett kommande datum")
    private LocalDate checkOutDate;
}



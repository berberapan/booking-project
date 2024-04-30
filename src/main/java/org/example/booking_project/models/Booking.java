package org.example.booking_project.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Bokningsnummer är obligatoriskt")
    @Size(min = 5, message = "Minst 5 tecken för Bokningsnummer, BN följt av siffror: BN1***")
    @Column(unique = true)
    private String bookingNr;

    @NotEmpty(message = "Kund är obligatorisk")
    @ManyToOne
    @JoinColumn
    private Customer customer;

    @ManyToOne
    @JoinColumn
    private Room room;

    private int bookedBeds;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

}

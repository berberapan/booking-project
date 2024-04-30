package org.example.booking_project.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {
    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "Rumsnummer är obligatoriskt")
    @Column(unique = true)
    private int roomNumber;

    @NotEmpty(message = "Rumstyp är obligatoriskt. Ange RoomTyp:single eller double")
    private RoomType roomType;

    @NotEmpty(message = "Max antal sängar är obligatoriskt")
    @Min(value = 1, message = "Rummet måste ha minst 1 säng")
    @Max(value = 4, message = "Rummet får inte ha fler än 4 sängar")
    private int maxBeds;

    @NotEmpty(message = "Pris/natt är obligatoriskt")
    @PositiveOrZero(message = "Rummet får inte ha ett negativt pris/natt")
    private int pricePerNight;

}

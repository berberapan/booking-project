package org.example.booking_project.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty(message = "Rummet måste ha ett rumsnummer")
    @Column(unique = true)
    private int roomNumber;

    @NotEmpty(message = "Ange RoomTyp:singel eller dubbel")
    private RoomType roomType;

    @NotEmpty(message = "Ange 1 - 4 sängar")
    @Min(value = 1, message = "Rummet måste ha minst 1 säng")
    @Max(value = 4, message = "Rummet får inte ha fler än 4 sängar")
    private int maxBeds;

    @NotEmpty(message = "Rummet måste ha pris/natt")
    @Min(value = 0, message = "Rummet får inte ha ett negativt pris/natt")
    private int pricePerNight;

}

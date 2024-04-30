package org.example.booking_project.Dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.booking_project.models.RoomType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomDTO {

    private Long id;

    @NotEmpty(message = "Rumsnummer är obligatoriskt")
    private int roomNumber;

    @NotEmpty(message = "Max antal sängar är obligatoriskt")
    @Min(value = 1, message = "Rummet måste ha minst 1 säng")
    @Max(value = 4, message = "Rummet får inte ha fler än 4 sängar")
    private int maxBeds;

    @NotEmpty(message = "Pris/natt är obligatoriskt")
    @PositiveOrZero(message = "Rummet får inte ha ett negativt pris/natt")
    private int pricePerNight;

    @NotEmpty(message = "Rumstyp är obligatoriskt. Ange RoomTyp:single eller double")
    private RoomType roomType;
}

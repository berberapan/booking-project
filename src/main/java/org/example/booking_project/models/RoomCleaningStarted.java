package org.example.booking_project.models;

import jakarta.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "RoomCleaningStarted")
public class RoomCleaningStarted extends EventBase {
    @NotEmpty(message = "Rumsnummer är obligatoriskt")
    @Size(min = 3, max = 3,message = "Rumsnummer måste vara 3 siffror")
    @Pattern(regexp = "^[0-9]*$", message = "Rumsnummer får endast innehålla siffror")
    public String RoomNo;

    @NotEmpty(message = "User är obligatoriskt")
    @Size(min = 3,message = "User måste vara minst 3 tecken")
    @Pattern(regexp = "^[A-Öa-ö. -]*$", message = "User får endast innehålla bokstäver,bindestreck och mellanslag")
    public String CleaningByUser;
}

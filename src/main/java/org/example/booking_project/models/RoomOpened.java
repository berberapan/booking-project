package org.example.booking_project.models;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "RoomOpened")
public class RoomOpened extends EventBase {
    @NotEmpty(message = "Rumsnummer är obligatoriskt")
    @Size(min = 3, max = 3,message = "Rumsnummer måste vara 3 siffror")
    @Pattern(regexp = "^[0-9]*$", message = "Rumsnummer får endast innehålla siffror")
    public String RoomNo;
}

package org.example.booking_project.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(name = "RoomCleaningStarted")
public class RoomCleaningStarted extends EventBase {
    public String RoomNo;
    public String CleaningByUser;
}

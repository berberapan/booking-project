package org.example.booking_project.models;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.*;


@Entity(name = "RoomCleaningStarted")
public class RoomCleaningStarted extends EventBase {
    public String RoomNo;
    public String CleaningByUser;
}

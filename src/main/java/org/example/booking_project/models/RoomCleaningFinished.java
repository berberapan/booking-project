package org.example.booking_project.models;

import jakarta.persistence.Entity;


@Entity(name = "RoomCleaningFinished")
public class RoomCleaningFinished extends EventBase {
    public String RoomNo;
    public String CleaningByUser;
}

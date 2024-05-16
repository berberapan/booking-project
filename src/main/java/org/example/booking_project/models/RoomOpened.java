package org.example.booking_project.models;

import jakarta.persistence.Entity;


@Entity(name = "RoomOpened")
public class RoomOpened extends EventBase {
    public String RoomNo;
}

package org.example.booking_project.models;

import jakarta.persistence.Entity;


@Entity(name = "RoomClosed")
public class RoomClosed extends EventBase {
    public String RoomNo;
}

package org.example.booking_project.models;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.*;


@Entity(name = "RoomClosed")
public class RoomClosed extends EventBase {
    public String RoomNo;
}

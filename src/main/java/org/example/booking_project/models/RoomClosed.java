package org.example.booking_project.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(name = "RoomClosed")
public class RoomClosed extends EventBase {
    public String RoomNo;
}

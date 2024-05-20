package org.example.booking_project.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(name = "RoomOpened")
public class RoomOpened extends EventBase {
    public String RoomNo;
}

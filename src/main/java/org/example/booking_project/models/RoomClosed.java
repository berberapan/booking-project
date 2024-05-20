package org.example.booking_project.models;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "RoomClosed")
public class RoomClosed extends EventBase {
    public String RoomNo;
}

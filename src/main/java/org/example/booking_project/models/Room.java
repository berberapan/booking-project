package org.example.booking_project.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private int roomNumber;

    private RoomType roomType;
    private int maxBeds;
    private int pricePerNight;

}

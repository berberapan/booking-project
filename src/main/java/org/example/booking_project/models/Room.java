package org.example.booking_project.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class Room {
        @Id
        @GeneratedValue
        Long id;

        @Column(unique=true)
        int roomNumber;

        RoomType roomType;
        int maxBeds;
        int pricePerNight;

    }

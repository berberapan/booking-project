package org.example.booking_project.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventBaseDTO {
    private Long id;
    private String type;
    private LocalDateTime timeStamp;
}
package org.example.booking_project.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventBaseDTO {
    public Long id;
    public String type;
    public LocalDateTime TimeStamp;
    public String roomNo;
    public String CleaningByUser;
}
package org.example.booking_project.Dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomClosedDTO extends EventBaseDTO {

    @NotEmpty
    public String RoomNo;

}

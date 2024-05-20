package org.example.booking_project.Dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomOpenedDTO extends EventBaseDTO {

    @NotEmpty
    public String RoomNo;
}

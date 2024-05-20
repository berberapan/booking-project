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
public class RoomCleaningStartedDTO extends EventBaseDTO {

    @NotEmpty
    public String RoomNo;

    @NotEmpty
    public String CleaningByUser;
}

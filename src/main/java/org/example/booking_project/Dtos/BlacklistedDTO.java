package org.example.booking_project.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlacklistedDTO {
    private String email;
    private String name;
    private boolean ok;
}

package org.example.booking_project.Dtos;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserDTO {
    private UUID id;
    private String username;
    private String password;
    private boolean admin;
    private boolean receptionist;
}



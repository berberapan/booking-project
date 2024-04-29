package org.example.booking_project.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {

    private Long id;

    private String customerNumber;

    private String customerName;

    private String phoneNumber;

    private String email;
}

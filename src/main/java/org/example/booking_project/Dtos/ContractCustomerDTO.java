package org.example.booking_project.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractCustomerDTO {
        private Long id;
        private String companyName;
        private String contactName;
        private String contactTitle;
        private String streetAddress;
        private String city;
        private String postalCode;
        private String country;
        private String phone;
        private String fax;

    }

package org.example.booking_project.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ContractCustomer {

    @Id
    @GeneratedValue
    private Long id;

    private String companyName;
    private String contactName;
    private String contactTitle;
    private String streetAddress;
    private String city;
    private int postalCode;
    private String country;
    private String phone;
    private String fax;

}



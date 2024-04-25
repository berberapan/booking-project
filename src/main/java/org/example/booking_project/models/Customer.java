package org.example.booking_project.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String customerNumber;

    private String customerName;
    private String phoneNumber;

    @Column(unique = true)
    private String email;

}

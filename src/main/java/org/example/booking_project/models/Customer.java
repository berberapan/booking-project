package org.example.booking_project.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "Kundnummer är obligatoriskt")
    @Size(min = 5, message = "Minst 5 tecken för kundnummer, CN följt av siffror: CN1***")
    @Column(unique = true)
    private String customerNumber;

    private String customerName;
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    public Customer( String customerName, String phoneNumber, String email){
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}

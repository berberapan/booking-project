package org.example.booking_project.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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

    @NotEmpty(message = "Kundnamn är obligatoriskt")
    @Size(min = 3, message = "Kundnamn måste vara minst 3 tecken")
    @Pattern(regexp="^[A-Öa-ö ]*$", message = "Kundnamn får endast innehålla bokstäver och mellanslag")
    private String customerName;

    @NotEmpty(message = "Telefonnummer är obligatoriskt")
    @Size(min = 9, message = "Telefonnummer måste ha minst 9 tecken")
    @Pattern(regexp="^[0-9 -]*$", message = "Telefonnummer får endast innehålla siffror,mellanslag och bindestreck")
    private String phoneNumber;

    @NotEmpty(message = "Email är obligatoriskt")
    @Email(message = "Ange en giltig email-address")
    @Column(unique = true)
    private String email;

    public Customer( String customerName, String phoneNumber, String email){
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}

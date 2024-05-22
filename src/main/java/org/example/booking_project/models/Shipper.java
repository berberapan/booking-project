package org.example.booking_project.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Shipper {
    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "Email är obligatoriskt")
    @Email(message = "Ange en giltig email-address")
    private String email;

    @NotEmpty(message = "Företagsnamn är obligatoriskt")
    private String companyName;

    @NotEmpty(message = "Kontaktnamn är obligatoriskt")
    @Size(min = 3, message = "Kontaktnamn måste vara minst 3 tecken")
    @Pattern(regexp = "^[A-Öa-ö ]*$", message = "Kontaktnamn får endast innehålla bokstäver och mellanslag")
    private String contactName;

    @NotEmpty(message = "Kontakttitel är obligatoriskt")
    @Size(min = 2, message = "Kontakttitel måste vara minst 2 tecken")
    private String contactTitle;

    @NotEmpty(message = "Adress är obligatoriskt")
    @Size(min = 3, message = "Adress måste vara minst 3 tecken")
    private String streetAddress;

    @NotEmpty(message = "Stad är obligatoriskt")
    @Pattern(regexp = "^[A-Öa-ö ]*$", message = "Stad får endast innehålla bokstäver och mellanslag")
    private String city;

    @NotEmpty(message = "Postnummer är obligatoriskt")
    @Size(min = 5, max = 5,message = "Postnummer måste vara 5 siffror")
    @Pattern(regexp = "^[0-9]*$", message = "Postnummer får endast innehålla siffror")
    private String postalCode;

    @NotEmpty(message = "Land är obligatoriskt")
    @Size(min = 2, message = "Land måste vara minst 2 tecken")
    @Pattern(regexp = "^[A-Öa-ö ]*$", message = "Land får endast innehålla bokstäver och mellanslag")
    private String country;

    @NotEmpty(message = "Telefonnummer är obligatoriskt")
    @Size(min = 9, message = "Telefonnummer måste ha minst 9 tecken")
    @Pattern(regexp = "^[0-9-]*$", message = "Telefonnummer får endast innehålla siffror och bindestreck")
    private String phone;

    @NotEmpty(message = "Faxnummer är obligatoriskt")
    @Size(min = 9, message = "Faxnummer måste ha minst 9 tecken")
    @Pattern(regexp = "^[0-9-]*$", message = "Faxnummer får endast innehålla siffror och bindestreck")
    private String fax;
}

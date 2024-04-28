package org.example.booking_project.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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

    @Generated(event = EventType.INSERT)
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

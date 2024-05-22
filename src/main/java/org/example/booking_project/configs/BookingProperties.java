package org.example.booking_project.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@ConfigurationPropertiesScan
@Configuration
@ConfigurationProperties(prefix = "bookings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingProperties {
    private double discountSundayNight;
    private double discountOverTwoNights;
    private double discountOverTenNights;
}

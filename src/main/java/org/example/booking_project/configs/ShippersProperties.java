package org.example.booking_project.configs;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@ConfigurationPropertiesScan
@Configuration
@ConfigurationProperties(prefix = "shippers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShippersProperties {
    public String fetchurl;
}

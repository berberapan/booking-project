package org.example.booking_project.configs;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@ConfigurationPropertiesScan
@Configuration
@ConfigurationProperties(prefix = "blacklist")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class BlacklistProperties {
    public String fetchUrl;
    public String postUrl;
    public String putUrl;
}

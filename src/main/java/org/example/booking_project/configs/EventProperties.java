package org.example.booking_project.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@ConfigurationPropertiesScan
@Configuration
@ConfigurationProperties(prefix = "events")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class EventProperties {
    public String queueName;
    public String factoryHost;
    public String factoryUsername;
    public String factoryPassword;
}
package com.example.adapter.output.repository.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class PropertiesRedisConfiguration {
    private int port;
    private String host;
    private long ttl;
    private String password;
}

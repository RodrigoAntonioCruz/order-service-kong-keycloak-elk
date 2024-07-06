package com.example.adapter.output.producer.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.kafka")
public class PropertiesProducerConfiguration {
    private long maxBlockMs;
    private String groupId;
    private String autoOffsetReset;
    private String bootstrapServers;
    private String producerKeySerializer;
    private String producerValueSerializer;
    private String topicOrderName;
    private String topicOrderDltName;
    private boolean saslEnabled;
    private String securityProtocol;
    private String saslMechanism;
    private String saslJaasConfig;
}

package com.example.adapter.output.producer.configuration.kafka;


import com.example.adapter.output.producer.configuration.properties.PropertiesProducerConfiguration;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@AllArgsConstructor
public class KafkaProducerConfiguration {


    private final PropertiesProducerConfiguration properties;

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> producer = new HashMap<>();
        producer.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        producer.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, properties.getProducerKeySerializer());
        producer.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, properties.getProducerValueSerializer());
        producer.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, properties.getMaxBlockMs());

        if (properties.isSaslEnabled()) {
            producer.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, properties.getSecurityProtocol());
            producer.put(SaslConfigs.SASL_MECHANISM, properties.getSaslMechanism());
            producer.put(SaslConfigs.SASL_JAAS_CONFIG, properties.getSaslJaasConfig());
        }

        return new DefaultKafkaProducerFactory<>(producer);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}

package com.example.adapter.input.consumer.configuration.kafka;


import com.example.adapter.input.consumer.configuration.properties.PropertiesConsumerConfiguration;
import com.example.adapter.output.producer.utils.Constants;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;


@EnableKafka
@Configuration
@AllArgsConstructor
public class KafkaConsumerConfiguration {

    private final PropertiesConsumerConfiguration properties;

    @Bean
    public ConsumerFactory<Object, Object> consumerFactory() {
        Map<String, Object> consumer = new HashMap<>();
        consumer.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        consumer.put(ConsumerConfig.GROUP_ID_CONFIG, properties.getGroupId());
        consumer.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, properties.getConsumerKeyDeserializer());
        consumer.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, properties.getConsumerValueDeserializer());

        if (properties.isSaslEnabled()) {
            consumer.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, properties.getSecurityProtocol());
            consumer.put(SaslConfigs.SASL_MECHANISM, properties.getSaslMechanism());
            consumer.put(SaslConfigs.SASL_JAAS_CONFIG, properties.getSaslJaasConfig());
        }

        return new DefaultKafkaConsumerFactory<>(consumer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Object, Object> listenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            ConsumerFactory<Object, Object> kafkaConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, kafkaConsumerFactory);
        factory.setRecordFilterStrategy(filter());
        return factory;
    }

    @Bean
    public RecordFilterStrategy<Object, Object> filter() {
        return consumerRecord -> {
            this.setupTraceId(consumerRecord);
            this.setupSessionId(consumerRecord);
            return false;
        };
    }

    private void setupMDCValue(ConsumerRecord<Object, Object> consumerRecord, String headerKey, String mdcKey, String defaultValue) {
        String value = Stream.of(Optional.ofNullable(consumerRecord.headers()).orElseGet(RecordHeaders::new).toArray())
                .filter(f -> headerKey.equalsIgnoreCase(f.key()))
                .map(f -> Optional.of(new String(f.value(), StandardCharsets.UTF_8)).orElseGet(String::new).replace("\"", ""))
                .findFirst()
                .orElse(defaultValue);
        MDC.put(mdcKey, value);
    }

    private void setupTraceId(ConsumerRecord<Object, Object> consumerRecord) {
        setupMDCValue(consumerRecord, Constants.TRACE_ID_KEY, Constants.TRACE_ID_KEY, UUID.randomUUID().toString());
    }

    private void setupSessionId(ConsumerRecord<Object, Object> consumerRecord) {
        setupMDCValue(consumerRecord, Constants.SESSION_ID_KEY, Constants.SESSION_ID_KEY, "");
    }
}

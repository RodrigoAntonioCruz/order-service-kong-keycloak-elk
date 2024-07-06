package com.example.adapter.output.producer;


import com.example.adapter.output.producer.configuration.properties.PropertiesProducerConfiguration;
import com.example.adapter.output.producer.utils.Constants;
import com.example.core.FileOrderLine;
import com.example.core.ports.output.SendOrderOutputPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class Producer implements SendOrderOutputPort {

    private final ObjectMapper mapper;

    private final PropertiesProducerConfiguration properties;

    private final KafkaTemplate<String, String> template;

    @Override
    public void send(FileOrderLine fileOrderLine) {
        try {
            log.info(Constants.LOG_KEY_MESSAGE + Constants.LOG_KEY_METHOD + Constants.LOG_KEY_ENTITY,
                    String.format("Início da publicação no tópico %s ", properties.getTopicOrderName()), Constants.LOG_KEY_SEND, fileOrderLine);

            MessageHeaders headers = this.getHeaders(fileOrderLine.getOrderId());
            template.setDefaultTopic(properties.getTopicOrderName());

            String message = mapper.writeValueAsString(fileOrderLine);
            template.send(new GenericMessage<>(message, headers));

            log.info(Constants.LOG_KEY_MESSAGE + Constants.LOG_KEY_METHOD + Constants.LOG_KEY_ENTITY,
                    String.format("Fim da publicação no tópico %s ", properties.getTopicOrderName()), Constants.LOG_KEY_SEND, fileOrderLine);
        } catch (JsonProcessingException e) {
            log.error(Constants.LOG_KEY_MESSAGE + Constants.LOG_KEY_METHOD + Constants.LOG_KEY_CAUSE,
                    String.format("Falha ao publicar a mesagem %s no tópico %s", fileOrderLine, properties.getTopicOrderName()), Constants.LOG_KEY_SEND, e.getMessage());
        }
    }

    private MessageHeaders getHeaders(Object id) {
        Map<String, Object> source = new HashMap<>();
        source.put(KafkaHeaders.KEY, Objects.toString(id, UUID.randomUUID().toString()));
        source.put(Constants.TRACE_ID_KEY, MDC.get(Constants.TRACE_ID_KEY));
        return new MessageHeaders(source);
    }
}
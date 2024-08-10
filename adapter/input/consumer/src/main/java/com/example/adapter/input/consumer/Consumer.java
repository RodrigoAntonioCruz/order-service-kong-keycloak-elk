package com.example.adapter.input.consumer;

import com.example.adapter.input.consumer.exception.BusinessException;
import com.example.adapter.input.consumer.exception.ExceptionResolver;
import com.example.adapter.output.producer.utils.Constants;
import com.example.core.FileOrderLine;
import com.example.core.Order;
import com.example.core.Product;
import com.example.core.User;
import com.example.core.ports.input.SaveOrderInputPort;
import com.example.core.ports.input.SaveProductInputPort;
import com.example.core.ports.input.SaveUserInputPort;
import com.example.core.ports.input.UnifyOrdersInputPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.FixedDelayStrategy;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@AllArgsConstructor
public class Consumer {

    private final ObjectMapper objectMapper;

    private final SaveUserInputPort userInputPort;

    private final SaveOrderInputPort orderInputPort;

    private final SaveProductInputPort productInputPort;

    private final UnifyOrdersInputPort unifyOrdersInputPort;
    private final AtomicInteger pendingOrdersCount = new AtomicInteger(0);
    private final AtomicBoolean isUnificationScheduled = new AtomicBoolean(false);
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     *
     * Esse é um teste rrrrrrrrrrrrrrrrrvv de CK
     *
     * **/
    @RetryableTopic(
            backoff = @Backoff(value = 1000),
            attempts = "${spring.kafka.max-attemp-retry}",
            autoCreateTopics = "${spring.kafka.auto-create-topics}",
            fixedDelayTopicStrategy = FixedDelayStrategy.SINGLE_TOPIC
    )
    @KafkaListener(topics = "${spring.kafka.topic-order-name}", groupId = "spring.kafka.group-id")
    public void consumer(@Payload String payload, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) long offset) {
        try {
            pendingOrdersCount.incrementAndGet();
            log.info(Constants.LOG_KEY_MESSAGE + Constants.LOG_KEY_METHOD + Constants.LOG_KEY_ENTITY,
                    String.format("Início do consumo no tópico %s, offset %d", topic, offset), Constants.LOG_METHOD_CONSUME, payload);

            FileOrderLine line = objectMapper.readValue(payload, FileOrderLine.class);

            userInputPort.save(new User(line.getUserId(), line.getUserName()));

            orderInputPort.save(new Order(line.getOrderId(), line.getUserId(), line.getValue(), line.getDate(), List.of(line.getProdId())));

            log.info(Constants.LOG_KEY_MESSAGE + Constants.LOG_KEY_METHOD + Constants.LOG_KEY_ENTITY,
                    String.format("Fim do consumo no tópico %s, offset %d", topic, offset), Constants.LOG_METHOD_CONSUME, payload);
        } catch (BusinessException | IOException e) {
            log.error(Constants.LOG_KEY_MESSAGE + Constants.LOG_KEY_METHOD + Constants.LOG_KEY_CAUSE,
                                 String.format("Falha ao ler mensagem do tópico %s, offset %d", topic, offset),
                                                 Constants.LOG_METHOD_CONSUME, ExceptionResolver.getCauseException(e));
        } finally {
            pendingOrdersCount.decrementAndGet();
        }
    }

    @PostConstruct
    private void scheduleUnification() {
        scheduler.scheduleAtFixedRate(() -> {
            if (pendingOrdersCount.get() == 0 && isUnificationScheduled.getAndSet(false)) {
                log.info("O UNIFICADOR DE PEDIDOS FOI CHAMADO AO FIM DO PROCESSO 1 VEZ");
                unifyOrdersInputPort.unifyAndDeleteDuplicates();
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    @DltHandler
    public void dlt(@Payload String payload, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) long offset) {
        log.error(Constants.LOG_KEY_MESSAGE + Constants.LOG_KEY_METHOD,
                String.format("Mensagem não pode ser processada, enviando para o tópico de DLT %s, offset %d", topic, offset), Constants.LOG_METHOD_DLT);
    }
}

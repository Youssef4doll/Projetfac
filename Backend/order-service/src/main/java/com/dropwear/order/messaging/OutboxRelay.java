package com.dropwear.order.messaging;

import java.time.Instant;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dropwear.order.domain.OutboxEvent;
import com.dropwear.order.repository.OutboxEventRepository;

/**
 * Transactional Outbox : la commande et son événement (E2/E3) sont écrits dans la même transaction,
 * puis ce relais publie les lignes non envoyées. Si Kafka est arrêté, rien n'est perdu.
 */
@Component
public class OutboxRelay {

    private final OutboxEventRepository outbox;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OutboxRelay(OutboxEventRepository outbox, KafkaTemplate<String, String> kafkaTemplate) {
        this.outbox = outbox;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedDelayString = "${dropwear.outbox.poll-interval-ms:2000}")
    @Transactional
    public void publishPending() {
        for (OutboxEvent event : outbox.findTop100ByPublishedAtIsNullOrderByCreatedAtAsc()) {
            kafkaTemplate.send(event.getTopic(), event.getAggregateId(), event.getPayload()).join();
            event.setPublishedAt(Instant.now());
        }
    }
}

package com.dropwear.order.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.dropwear.order.events.EventEnvelope;
import com.dropwear.order.events.Topics;
import com.dropwear.order.events.CommandeExpedieeEvent;
import com.dropwear.order.events.CommandeLivreeEvent;

/**
 * Consommateurs Kafka de order-service (consumer group = spring.application.name).
 * Erreur : 3 nouvelles tentatives puis topic .DLT (voir KafkaConfig).
 */
@Component
public class OrderEventListener {

    private static final Logger log = LoggerFactory.getLogger(OrderEventListener.class);

    private final ObjectMapper objectMapper;

    public OrderEventListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /** E6 CommandeExpedieeEvent (« livraison.expediee ») : statut EXPEDIEE + shippedAt. */
    @KafkaListener(topics = Topics.LIVRAISON_EXPEDIEE)
    public void onCommandeExpediee(String message) throws JsonProcessingException {
        EventEnvelope<CommandeExpedieeEvent> event = objectMapper.readValue(message, new TypeReference<EventEnvelope<CommandeExpedieeEvent>>() { });
        log.info("CommandeExpedieeEvent reçu : eventId={}", event.eventId());
        // TODO Validation 2 : ignorer si eventId déjà présent dans processed_events (idempotence), puis statut EXPEDIEE + shippedAt.
    }

    /** E7 CommandeLivreeEvent (« livraison.livree ») : statut LIVREE + deliveredAt (début du délai de 14 jours). */
    @KafkaListener(topics = Topics.LIVRAISON_LIVREE)
    public void onCommandeLivree(String message) throws JsonProcessingException {
        EventEnvelope<CommandeLivreeEvent> event = objectMapper.readValue(message, new TypeReference<EventEnvelope<CommandeLivreeEvent>>() { });
        log.info("CommandeLivreeEvent reçu : eventId={}", event.eventId());
        // TODO Validation 2 : ignorer si eventId déjà présent dans processed_events (idempotence), puis statut LIVREE + deliveredAt (début du délai de 14 jours).
    }
}

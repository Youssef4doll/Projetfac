package com.dropwear.returns.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.dropwear.returns.events.EventEnvelope;
import com.dropwear.returns.events.Topics;
import com.dropwear.returns.events.RetourFinaliseEvent;

/**
 * Consommateurs Kafka de return-service (consumer group = spring.application.name).
 * Erreur : 3 nouvelles tentatives puis topic .DLT (voir KafkaConfig).
 */
@Component
public class ReturnEventListener {

    private static final Logger log = LoggerFactory.getLogger(ReturnEventListener.class);

    private final ObjectMapper objectMapper;

    public ReturnEventListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /** E11 RetourFinaliseEvent (« retour.finalise ») : statut CLOTUREE + outcome. */
    @KafkaListener(topics = Topics.RETOUR_FINALISE)
    public void onRetourFinalise(String message) throws JsonProcessingException {
        EventEnvelope<RetourFinaliseEvent> event = objectMapper.readValue(message, new TypeReference<EventEnvelope<RetourFinaliseEvent>>() { });
        log.info("RetourFinaliseEvent reçu : eventId={}", event.eventId());
        // TODO Validation 2 : ignorer si eventId déjà présent dans processed_events (idempotence), puis statut CLOTUREE + outcome.
    }
}

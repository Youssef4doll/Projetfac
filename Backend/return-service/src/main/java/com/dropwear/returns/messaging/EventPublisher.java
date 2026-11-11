package com.dropwear.returns.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.dropwear.returns.events.EventEnvelope;

/**
 * Publication des événements de return-service :
 *   E9 RetourApprouveEvent -> retour.approuve
 *   E10 RetourRecuEvent -> retour.recu
 * La clé de partition est l'identifiant de l'agrégat (ordre garanti par commande / RMA / SKU).
 */
@Component
public class EventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final String source;

    public EventPublisher(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper,
                          @Value("${spring.application.name}") String source) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.source = source;
    }

    public String toJson(Object payload) {
        try {
            return objectMapper.writeValueAsString(EventEnvelope.of(source, payload));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Sérialisation de l'événement impossible", e);
        }
    }

    public void publish(String topic, String key, Object payload) {
        kafkaTemplate.send(topic, key, toJson(payload));
    }
}

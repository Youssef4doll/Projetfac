package com.dropwear.ticketing.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.dropwear.ticketing.events.EventEnvelope;
import com.dropwear.ticketing.events.Topics;
import com.dropwear.ticketing.events.LivraisonRetardeeEvent;
import com.dropwear.ticketing.events.RetourApprouveEvent;

/**
 * Consommateurs Kafka de ticketing-service (consumer group = spring.application.name).
 * Erreur : 3 nouvelles tentatives puis topic .DLT (voir KafkaConfig).
 */
@Component
public class TicketingEventListener {

    private static final Logger log = LoggerFactory.getLogger(TicketingEventListener.class);

    private final ObjectMapper objectMapper;

    public TicketingEventListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /** E8 LivraisonRetardeeEvent (« livraison.retardee ») : crée un ticket RETARD_LIVRAISON, source SYSTEME, priorité HAUTE (S6 pour les coordonnées). */
    @KafkaListener(topics = Topics.LIVRAISON_RETARDEE)
    public void onLivraisonRetardee(String message) throws JsonProcessingException {
        EventEnvelope<LivraisonRetardeeEvent> event = objectMapper.readValue(message, new TypeReference<EventEnvelope<LivraisonRetardeeEvent>>() { });
        log.info("LivraisonRetardeeEvent reçu : eventId={}", event.eventId());
        // TODO Validation 2 : ignorer si eventId déjà présent dans processed_events (idempotence), puis crée un ticket RETARD_LIVRAISON, source SYSTEME, priorité HAUTE (S6 pour les coordonnées).
    }

    /** E9 RetourApprouveEvent (« retour.approuve ») : si ticketId : message système + ticket RESOLU. */
    @KafkaListener(topics = Topics.RETOUR_APPROUVE)
    public void onRetourApprouve(String message) throws JsonProcessingException {
        EventEnvelope<RetourApprouveEvent> event = objectMapper.readValue(message, new TypeReference<EventEnvelope<RetourApprouveEvent>>() { });
        log.info("RetourApprouveEvent reçu : eventId={}", event.eventId());
        // TODO Validation 2 : ignorer si eventId déjà présent dans processed_events (idempotence), puis si ticketId : message système + ticket RESOLU.
    }
}

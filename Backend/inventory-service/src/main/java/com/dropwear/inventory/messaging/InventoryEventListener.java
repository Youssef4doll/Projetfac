package com.dropwear.inventory.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.dropwear.inventory.events.EventEnvelope;
import com.dropwear.inventory.events.Topics;
import com.dropwear.inventory.events.VarianteCreeeEvent;
import com.dropwear.inventory.events.CommandeValideeEvent;
import com.dropwear.inventory.events.CommandeAnnuleeEvent;
import com.dropwear.inventory.events.RetourRecuEvent;

/**
 * Consommateurs Kafka de inventory-service (consumer group = spring.application.name).
 * Erreur : 3 nouvelles tentatives puis topic .DLT (voir KafkaConfig).
 */
@Component
public class InventoryEventListener {

    private static final Logger log = LoggerFactory.getLogger(InventoryEventListener.class);

    private final ObjectMapper objectMapper;

    public InventoryEventListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /** E1 VarianteCreeeEvent (« catalogue.variante-creee ») : crée le StockItem (quantité 0, seuil par défaut 5). */
    @KafkaListener(topics = Topics.CATALOGUE_VARIANTE_CREEE)
    public void onVarianteCreee(String message) throws JsonProcessingException {
        EventEnvelope<VarianteCreeeEvent> event = objectMapper.readValue(message, new TypeReference<EventEnvelope<VarianteCreeeEvent>>() { });
        log.info("VarianteCreeeEvent reçu : eventId={}", event.eventId());
        // TODO Validation 2 : ignorer si eventId déjà présent dans processed_events (idempotence), puis crée le StockItem (quantité 0, seuil par défaut 5).
    }

    /** E2 CommandeValideeEvent (« commande.validee ») : réservation CONFIRMEE : onHand -= q, reserved -= q, mouvement SORTIE (+ E4 si seuil atteint). */
    @KafkaListener(topics = Topics.COMMANDE_VALIDEE)
    public void onCommandeValidee(String message) throws JsonProcessingException {
        EventEnvelope<CommandeValideeEvent> event = objectMapper.readValue(message, new TypeReference<EventEnvelope<CommandeValideeEvent>>() { });
        log.info("CommandeValideeEvent reçu : eventId={}", event.eventId());
        // TODO Validation 2 : ignorer si eventId déjà présent dans processed_events (idempotence), puis réservation CONFIRMEE : onHand -= q, reserved -= q, mouvement SORTIE (+ E4 si seuil atteint).
    }

    /** E3 CommandeAnnuleeEvent (« commande.annulee ») : réservation LIBEREE (ou réintégration si déjà confirmée). */
    @KafkaListener(topics = Topics.COMMANDE_ANNULEE)
    public void onCommandeAnnulee(String message) throws JsonProcessingException {
        EventEnvelope<CommandeAnnuleeEvent> event = objectMapper.readValue(message, new TypeReference<EventEnvelope<CommandeAnnuleeEvent>>() { });
        log.info("CommandeAnnuleeEvent reçu : eventId={}", event.eventId());
        // TODO Validation 2 : ignorer si eventId déjà présent dans processed_events (idempotence), puis réservation LIBEREE (ou réintégration si déjà confirmée).
    }

    /** E10 RetourRecuEvent (« retour.recu ») : réintègre les articles REVENDABLE + confirme la réservation de remplacement (échange). */
    @KafkaListener(topics = Topics.RETOUR_RECU)
    public void onRetourRecu(String message) throws JsonProcessingException {
        EventEnvelope<RetourRecuEvent> event = objectMapper.readValue(message, new TypeReference<EventEnvelope<RetourRecuEvent>>() { });
        log.info("RetourRecuEvent reçu : eventId={}", event.eventId());
        // TODO Validation 2 : ignorer si eventId déjà présent dans processed_events (idempotence), puis réintègre les articles REVENDABLE + confirme la réservation de remplacement (échange).
    }
}

package com.dropwear.fulfillment.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.dropwear.fulfillment.events.EventEnvelope;
import com.dropwear.fulfillment.events.Topics;
import com.dropwear.fulfillment.events.CommandeValideeEvent;
import com.dropwear.fulfillment.events.CommandeAnnuleeEvent;
import com.dropwear.fulfillment.events.RetourRecuEvent;

/**
 * Consommateurs Kafka de fulfillment-service (consumer group = spring.application.name).
 * Erreur : 3 nouvelles tentatives puis topic .DLT (voir KafkaConfig).
 */
@Component
public class FulfillmentEventListener {

    private static final Logger log = LoggerFactory.getLogger(FulfillmentEventListener.class);

    private final ObjectMapper objectMapper;

    public FulfillmentEventListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /** E2 CommandeValideeEvent (« commande.validee ») : facture EMISE, paiement (EN_ATTENTE si COD, PAYE si CARTE), une Shipment par fournisseur + bordereau. */
    @KafkaListener(topics = Topics.COMMANDE_VALIDEE)
    public void onCommandeValidee(String message) throws JsonProcessingException {
        EventEnvelope<CommandeValideeEvent> event = objectMapper.readValue(message, new TypeReference<EventEnvelope<CommandeValideeEvent>>() { });
        log.info("CommandeValideeEvent reçu : eventId={}", event.eventId());
        // TODO Validation 2 : ignorer si eventId déjà présent dans processed_events (idempotence), puis facture EMISE, paiement (EN_ATTENTE si COD, PAYE si CARTE), une Shipment par fournisseur + bordereau.
    }

    /** E3 CommandeAnnuleeEvent (« commande.annulee ») : expéditions ANNULEE (si non expédiées), avoir, paiement annulé. */
    @KafkaListener(topics = Topics.COMMANDE_ANNULEE)
    public void onCommandeAnnulee(String message) throws JsonProcessingException {
        EventEnvelope<CommandeAnnuleeEvent> event = objectMapper.readValue(message, new TypeReference<EventEnvelope<CommandeAnnuleeEvent>>() { });
        log.info("CommandeAnnuleeEvent reçu : eventId={}", event.eventId());
        // TODO Validation 2 : ignorer si eventId déjà présent dans processed_events (idempotence), puis expéditions ANNULEE (si non expédiées), avoir, paiement annulé.
    }

    /** E10 RetourRecuEvent (« retour.recu ») : RETOUR : Refund + avoir ; ECHANGE : Shipment REMPLACEMENT_ECHANGE (puis E11). */
    @KafkaListener(topics = Topics.RETOUR_RECU)
    public void onRetourRecu(String message) throws JsonProcessingException {
        EventEnvelope<RetourRecuEvent> event = objectMapper.readValue(message, new TypeReference<EventEnvelope<RetourRecuEvent>>() { });
        log.info("RetourRecuEvent reçu : eventId={}", event.eventId());
        // TODO Validation 2 : ignorer si eventId déjà présent dans processed_events (idempotence), puis RETOUR : Refund + avoir ; ECHANGE : Shipment REMPLACEMENT_ECHANGE (puis E11).
    }
}

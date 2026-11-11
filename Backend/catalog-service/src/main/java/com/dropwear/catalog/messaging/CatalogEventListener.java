package com.dropwear.catalog.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.dropwear.catalog.events.EventEnvelope;
import com.dropwear.catalog.events.Topics;
import com.dropwear.catalog.events.StockFaibleEvent;
import com.dropwear.catalog.events.StockReapprovisionneEvent;

/**
 * Consommateurs Kafka de catalog-service (consumer group = spring.application.name).
 * Erreur : 3 nouvelles tentatives puis topic .DLT (voir KafkaConfig).
 */
@Component
public class CatalogEventListener {

    private static final Logger log = LoggerFactory.getLogger(CatalogEventListener.class);

    private final ObjectMapper objectMapper;

    public CatalogEventListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /** E4 StockFaibleEvent (« stock.faible ») : lowStock = true (outOfStock = true si available = 0). */
    @KafkaListener(topics = Topics.STOCK_FAIBLE)
    public void onStockFaible(String message) throws JsonProcessingException {
        EventEnvelope<StockFaibleEvent> event = objectMapper.readValue(message, new TypeReference<EventEnvelope<StockFaibleEvent>>() { });
        log.info("StockFaibleEvent reçu : eventId={}", event.eventId());
        // TODO Validation 2 : ignorer si eventId déjà présent dans processed_events (idempotence), puis lowStock = true (outOfStock = true si available = 0).
    }

    /** E5 StockReapprovisionneEvent (« stock.reapprovisionne ») : lowStock = false, outOfStock = false. */
    @KafkaListener(topics = Topics.STOCK_REAPPROVISIONNE)
    public void onStockReapprovisionne(String message) throws JsonProcessingException {
        EventEnvelope<StockReapprovisionneEvent> event = objectMapper.readValue(message, new TypeReference<EventEnvelope<StockReapprovisionneEvent>>() { });
        log.info("StockReapprovisionneEvent reçu : eventId={}", event.eventId());
        // TODO Validation 2 : ignorer si eventId déjà présent dans processed_events (idempotence), puis lowStock = false, outOfStock = false.
    }
}

package com.dropwear.returns.events;

import java.time.Instant;
import java.util.UUID;

/** Enveloppe commune à tous les événements DropWear (eventId = clé d'idempotence côté consommateur). */
public record EventEnvelope<T>(UUID eventId, String eventType, Instant occurredAt, String source, int version, T payload) {

    public static <T> EventEnvelope<T> of(String source, T payload) {
        return new EventEnvelope<>(UUID.randomUUID(), payload.getClass().getSimpleName(), Instant.now(), source, 1, payload);
    }
}

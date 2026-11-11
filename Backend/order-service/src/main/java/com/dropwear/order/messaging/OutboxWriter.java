package com.dropwear.order.messaging;

import java.time.Instant;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dropwear.order.domain.OutboxEvent;
import com.dropwear.order.repository.OutboxEventRepository;

/** À appeler DANS la transaction qui modifie la commande (Propagation.MANDATORY). */
@Component
public class OutboxWriter {

    private final OutboxEventRepository outbox;
    private final EventPublisher publisher;

    public OutboxWriter(OutboxEventRepository outbox, EventPublisher publisher) {
        this.outbox = outbox;
        this.publisher = publisher;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void enqueue(String topic, String aggregateId, Object payload) {
        OutboxEvent event = new OutboxEvent();
        event.setTopic(topic);
        event.setAggregateId(aggregateId);
        event.setEventType(payload.getClass().getSimpleName());
        event.setPayload(publisher.toJson(payload));
        event.setCreatedAt(Instant.now());
        outbox.save(event);
    }
}

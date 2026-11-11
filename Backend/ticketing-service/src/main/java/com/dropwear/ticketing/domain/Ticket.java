package com.dropwear.ticketing.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

/** Un ticket et son fil de messages = un document (ajout de message par $push atomique). OUVERT -> EN_COURS -> RESOLU. */
@Document(collection = "tickets")
@Getter
@Setter
@NoArgsConstructor
public class Ticket {

    @Id
    private String id;

    @Indexed(unique = true)
    private String ticketNumber;

    @Indexed
    private String customerId;

    private String customerName;

    private String customerEmail;

    @Indexed
    private String orderId;

    private String rmaId;

    private TicketCategory category;

    private String subject;

    private Priority priority;

    @Indexed
    private TicketStatus status;

    private TicketSource source;

    private boolean orderVerified;

    @Indexed
    private String assignedAgentId;

    private Instant slaDueAt;

    private List<Message> messages = new ArrayList<>();

    private List<Assignment> assignments = new ArrayList<>();

    private Instant createdAt;

    private Instant updatedAt;

    private Instant resolvedAt;

    private String resolutionNote;
}

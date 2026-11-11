package com.dropwear.ticketing.web.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import com.dropwear.ticketing.domain.*;

/** Contrats d'échange REST de ticketing-service (records immuables). */
public final class TicketingDtos {

    private TicketingDtos() {
    }

    public record AttachmentDto(String fileName, String url, String contentType, long sizeBytes) {
    }

    public record CreateTicketRequest(@NotNull TicketCategory category, @NotBlank String subject, @NotBlank String message, String orderId, List<AttachmentDto> attachments) {
    }

    public record MessageDto(String messageId, String authorId, AuthorRole authorRole, String content, List<AttachmentDto> attachments, Instant sentAt) {
    }

    public record NewMessageRequest(@NotBlank String content, List<AttachmentDto> attachments) {
    }

    public record TicketDto(String id, String ticketNumber, String customerId, String customerName, String orderId, String rmaId, TicketCategory category, String subject, Priority priority, TicketStatus status, TicketSource source, boolean orderVerified, String assignedAgentId, Instant slaDueAt, List<MessageDto> messages, Instant createdAt, Instant resolvedAt) {
    }

    public record TicketSummaryDto(String id, String ticketNumber, TicketCategory category, String subject, Priority priority, TicketStatus status, String assignedAgentId, Instant createdAt) {
    }

    public record AssignRequest(@NotBlank String agentId) {
    }

    public record StatusRequest(@NotNull TicketStatus status, String resolutionNote) {
    }

    public record PriorityRequest(@NotNull Priority priority) {
    }
}

package com.dropwear.ticketing.websocket;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.dropwear.ticketing.web.dto.TicketingDtos.NewMessageRequest;

/** SEND /app/tickets/{ticketId}/messages : persiste le message dans le ticket puis le diffuse aux abonnés. */
@Controller
public class TicketChatController {

    private final SimpMessagingTemplate messagingTemplate;

    public TicketChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/tickets/{ticketId}/messages")
    public void onMessage(@DestinationVariable String ticketId, @Payload NewMessageRequest request, Principal principal) {
        // TODO Validation 2 : $push du message dans Ticket.messages (persistance AVANT diffusion), puis :
        // messagingTemplate.convertAndSend("/topic/tickets/" + ticketId, messageDto);
    }
}

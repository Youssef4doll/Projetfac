package com.dropwear.ticketing.websocket;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import com.dropwear.ticketing.config.KeycloakJwtConverter;

/** Vérifie le JWT transmis dans la trame STOMP CONNECT (en-tête « Authorization: Bearer ... »). */
@Component
public class WebSocketAuthInterceptor implements ChannelInterceptor {

    private final JwtDecoder jwtDecoder;

    public WebSocketAuthInterceptor(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            String header = accessor.getFirstNativeHeader("Authorization");
            if (header == null || !header.startsWith("Bearer ")) {
                throw new IllegalArgumentException("JWT manquant dans la trame CONNECT");
            }
            try {
                Jwt jwt = jwtDecoder.decode(header.substring(7));
                accessor.setUser(new JwtAuthenticationToken(jwt, KeycloakJwtConverter.extractRealmRoles(jwt), jwt.getSubject()));
            } catch (JwtException e) {
                throw new IllegalArgumentException("JWT invalide", e);
            }
        }
        // TODO Validation 2 : sur SUBSCRIBE /topic/tickets/{id}, vérifier propriétaire ou agent assigné.
        return message;
    }
}

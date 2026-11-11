package com.dropwear.ticketing.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/** S6 — ticketing-service -> user-service : coordonnées du client pour un ticket automatique (pas de JWT client). */
@FeignClient(name = "user-service", path = "/internal/users")
public interface UserClient {

    @GetMapping("/{userId}")
    UserContact getContact(@PathVariable("userId") String userId);

    record UserContact(UUID userId, String fullName, String email, String phone) {
    }
}

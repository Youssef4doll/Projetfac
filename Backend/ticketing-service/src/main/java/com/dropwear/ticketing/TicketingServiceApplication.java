package com.dropwear.ticketing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * ticketing-service — DropWear.
 * Squelette Validation 1 : modèle, contrat d'API, événements et interactions. Logique métier à implémenter.
 */
@SpringBootApplication
@EnableFeignClients
public class TicketingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketingServiceApplication.class, args);
    }
}

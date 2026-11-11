package com.dropwear.fulfillment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * fulfillment-service — DropWear.
 * Squelette Validation 1 : modèle, contrat d'API, événements et interactions. Logique métier à implémenter.
 */
@SpringBootApplication
@EnableScheduling
public class FulfillmentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FulfillmentServiceApplication.class, args);
    }
}

package com.dropwear.returns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * return-service — DropWear.
 * Squelette Validation 1 : modèle, contrat d'API, événements et interactions. Logique métier à implémenter.
 */
@SpringBootApplication
@EnableFeignClients
public class ReturnServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReturnServiceApplication.class, args);
    }
}

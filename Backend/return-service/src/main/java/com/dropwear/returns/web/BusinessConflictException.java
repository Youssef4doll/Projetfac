package com.dropwear.returns.web;

/** Conflit métier (stock insuffisant, transition de statut invalide…) : traduit en 409. */
public class BusinessConflictException extends RuntimeException {

    public BusinessConflictException(String message) {
        super(message);
    }
}

package com.dropwear.catalog.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/** Validation 1 : seuls les contrats sont livrés, chaque endpoint répond 501 Not Implemented. */
public final class Stubs {

    private Stubs() {
    }

    public static ResponseStatusException notImplemented() {
        return new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED,
                "Squelette Validation 1 : logique métier non implémentée");
    }
}

package com.dropwear.catalog.web;

/** Un service appelé est indisponible (timeout, circuit ouvert…) : traduit en 503. */
public class DependencyUnavailableException extends RuntimeException {

    public DependencyUnavailableException(String dependency, Throwable cause) {
        super(dependency + " temporairement indisponible, réessayez dans quelques instants", cause);
    }
}

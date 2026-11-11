package com.dropwear.order.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Réponses d'erreur homogènes au format RFC 7807 (application/problem+json). */
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(DependencyUnavailableException.class)
    ProblemDetail handleUnavailable(DependencyUnavailableException exception) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.SERVICE_UNAVAILABLE, exception.getMessage());
        problem.setTitle("Service dépendant indisponible");
        return problem;
    }

    @ExceptionHandler(BusinessConflictException.class)
    ProblemDetail handleConflict(BusinessConflictException exception) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
        problem.setTitle("Conflit métier");
        return problem;
    }
}

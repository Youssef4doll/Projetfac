package com.dropwear.inventory.scheduling;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dropwear.inventory.domain.ReservationStatus;
import com.dropwear.inventory.repository.StockReservationRepository;

/**
 * Verrouillage temporaire : libère chaque minute les réservations ACTIVE dont expiresAt est dépassé
 * (compensation automatique si la commande n'aboutit pas). Remplace un TTL Redis.
 */
@Component
public class ReservationExpiryJob {

    private static final Logger log = LoggerFactory.getLogger(ReservationExpiryJob.class);

    private final StockReservationRepository reservations;

    public ReservationExpiryJob(StockReservationRepository reservations) {
        this.reservations = reservations;
    }

    @Scheduled(fixedRate = 60_000)
    public void releaseExpiredReservations() {
        int expired = reservations.findByStatusAndExpiresAtBefore(ReservationStatus.ACTIVE, Instant.now()).size();
        log.debug("{} réservation(s) expirée(s) à libérer", expired);
        // TODO Validation 2 : statut EXPIREE, quantityReserved -= q, mouvement LIBERATION (dans une transaction).
    }
}

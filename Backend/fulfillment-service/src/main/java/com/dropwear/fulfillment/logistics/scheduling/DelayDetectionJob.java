package com.dropwear.fulfillment.logistics.scheduling;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Détecte les expéditions en retard (date estimée dépassée, ou fournisseur au-delà de son délai d'expédition)
 * et publie LivraisonRetardeeEvent (E8) : ticket automatique côté support.
 */
@Component
public class DelayDetectionJob {

    @Scheduled(cron = "${dropwear.delay-detection.cron:0 0 * * * *}")
    public void detectDelays() {
        // TODO Validation 2 : rechercher les Shipment non livrées en retard et publier E8 (une seule fois par expédition).
    }
}

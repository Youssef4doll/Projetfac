package com.dropwear.ticketing.events;

/** Topics Kafka utilisés par ticketing-service (référentiel : Documentation/00, §3.2). */
public final class Topics {

    private Topics() {
    }

    /** E8 LivraisonRetardeeEvent */
    public static final String LIVRAISON_RETARDEE = "livraison.retardee";

    /** E9 RetourApprouveEvent */
    public static final String RETOUR_APPROUVE = "retour.approuve";
}

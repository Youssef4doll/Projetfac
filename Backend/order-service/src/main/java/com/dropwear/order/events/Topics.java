package com.dropwear.order.events;

/** Topics Kafka utilisés par order-service (référentiel : Documentation/00, §3.2). */
public final class Topics {

    private Topics() {
    }

    /** E2 CommandeValideeEvent */
    public static final String COMMANDE_VALIDEE = "commande.validee";

    /** E3 CommandeAnnuleeEvent */
    public static final String COMMANDE_ANNULEE = "commande.annulee";

    /** E6 CommandeExpedieeEvent */
    public static final String LIVRAISON_EXPEDIEE = "livraison.expediee";

    /** E7 CommandeLivreeEvent */
    public static final String LIVRAISON_LIVREE = "livraison.livree";
}

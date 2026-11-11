package com.dropwear.fulfillment.events;

/** Topics Kafka utilisés par fulfillment-service (référentiel : Documentation/00, §3.2). */
public final class Topics {

    private Topics() {
    }

    /** E6 CommandeExpedieeEvent */
    public static final String LIVRAISON_EXPEDIEE = "livraison.expediee";

    /** E7 CommandeLivreeEvent */
    public static final String LIVRAISON_LIVREE = "livraison.livree";

    /** E8 LivraisonRetardeeEvent */
    public static final String LIVRAISON_RETARDEE = "livraison.retardee";

    /** E11 RetourFinaliseEvent */
    public static final String RETOUR_FINALISE = "retour.finalise";

    /** E2 CommandeValideeEvent */
    public static final String COMMANDE_VALIDEE = "commande.validee";

    /** E3 CommandeAnnuleeEvent */
    public static final String COMMANDE_ANNULEE = "commande.annulee";

    /** E10 RetourRecuEvent */
    public static final String RETOUR_RECU = "retour.recu";
}

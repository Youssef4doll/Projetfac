package com.dropwear.inventory.events;

/** Topics Kafka utilisés par inventory-service (référentiel : Documentation/00, §3.2). */
public final class Topics {

    private Topics() {
    }

    /** E4 StockFaibleEvent */
    public static final String STOCK_FAIBLE = "stock.faible";

    /** E5 StockReapprovisionneEvent */
    public static final String STOCK_REAPPROVISIONNE = "stock.reapprovisionne";

    /** E1 VarianteCreeeEvent */
    public static final String CATALOGUE_VARIANTE_CREEE = "catalogue.variante-creee";

    /** E2 CommandeValideeEvent */
    public static final String COMMANDE_VALIDEE = "commande.validee";

    /** E3 CommandeAnnuleeEvent */
    public static final String COMMANDE_ANNULEE = "commande.annulee";

    /** E10 RetourRecuEvent */
    public static final String RETOUR_RECU = "retour.recu";
}

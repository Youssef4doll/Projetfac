package com.dropwear.catalog.events;

/** Topics Kafka utilisés par catalog-service (référentiel : Documentation/00, §3.2). */
public final class Topics {

    private Topics() {
    }

    /** E1 VarianteCreeeEvent */
    public static final String CATALOGUE_VARIANTE_CREEE = "catalogue.variante-creee";

    /** E4 StockFaibleEvent */
    public static final String STOCK_FAIBLE = "stock.faible";

    /** E5 StockReapprovisionneEvent */
    public static final String STOCK_REAPPROVISIONNE = "stock.reapprovisionne";
}

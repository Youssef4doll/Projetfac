package com.dropwear.returns.events;

/** Topics Kafka utilisés par return-service (référentiel : Documentation/00, §3.2). */
public final class Topics {

    private Topics() {
    }

    /** E9 RetourApprouveEvent */
    public static final String RETOUR_APPROUVE = "retour.approuve";

    /** E10 RetourRecuEvent */
    public static final String RETOUR_RECU = "retour.recu";

    /** E11 RetourFinaliseEvent */
    public static final String RETOUR_FINALISE = "retour.finalise";
}

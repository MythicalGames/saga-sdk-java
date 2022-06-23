package games.mythical.saga.sdk.client.model;

public interface SagaPaymentData {
    enum Type {
        CYBERSOURCE,
        UPHOLD
    }

    Type getType();

    boolean isDefaultMethod();

    default SagaCybersourcePaymentData asCybersource() { return null; }

    default SagaUpholdPaymentData asUphold() { return null; }
}

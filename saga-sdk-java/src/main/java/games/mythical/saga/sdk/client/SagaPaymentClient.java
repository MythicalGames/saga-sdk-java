package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.model.SagaPaymentMethod;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.payment.PaymentServiceGrpc;
import games.mythical.saga.sdk.proto.api.payments.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class SagaPaymentClient extends AbstractSagaClient {

    private PaymentServiceGrpc.PaymentServiceBlockingStub serviceBlockingStub;
    public SagaPaymentClient() throws SagaException {
        super();
    }

    @Override
    void initStub() {
        serviceBlockingStub = PaymentServiceGrpc.newBlockingStub(channel).withCallCredentials(addAuthentication());
    }

    public Optional<SagaPaymentMethod> createPaymentMethod(String oauthId,
                                                           CardPaymentData cardPaymentData,
                                                           Address address) {
        var request = CreatePaymentMethodRequest.newBuilder()
                .setOauthId(oauthId)
                .setCardPaymentData(cardPaymentData)
                .setAddress(address)
                .build();

        var paymentMethodProto = serviceBlockingStub.createPaymentMethod(request);
        return Optional.of(SagaPaymentMethod.fromProto(paymentMethodProto));
    }

    public Optional<SagaPaymentMethod> updatePaymentMethod(String oauthId,
                                                           CardPaymentData cardPaymentData,
                                                           Address address) {
        var request = UpdatePaymentMethodRequest.newBuilder()
                .setOauthId(oauthId)
                .setCardPaymentData(cardPaymentData)
                .setAddress(address)
                .build();

        var paymentMethodProto = serviceBlockingStub.updatePaymentMethod(request);
        return Optional.of(SagaPaymentMethod.fromProto(paymentMethodProto));
    }

    public Optional<SagaPaymentMethod> deletePaymentMethod(String oauthId,
                                                           CardPaymentData cardPaymentData) {
        var request = DeletePaymentMethodRequest.newBuilder()
                .setOauthId(oauthId)
                .setCardPaymentData(cardPaymentData)
                .build();

        var paymentMethodProto = serviceBlockingStub.deletePaymentMethod(request);
        return Optional.of(SagaPaymentMethod.fromProto(paymentMethodProto));
    }

    public Optional<SagaPaymentMethod> getPaymentMethod(String oauthId) {
        var request = GetPaymentMethodRequest.newBuilder()
                .setOauthId(oauthId)
                .build();

        var paymentMethodProto = serviceBlockingStub.getPaymentMethod(request);
        return Optional.of(SagaPaymentMethod.fromProto(paymentMethodProto));
    }
}

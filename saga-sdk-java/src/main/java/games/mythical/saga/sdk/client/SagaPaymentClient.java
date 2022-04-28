package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.SagaPaymentExecutor;
import games.mythical.saga.sdk.client.model.SagaAddress;
import games.mythical.saga.sdk.client.model.SagaPaymentMethod;
import games.mythical.saga.sdk.client.observer.SagaStatusUpdateObserver;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.payment.*;
import games.mythical.saga.sdk.proto.common.payment.PaymentProviderId;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class SagaPaymentClient extends AbstractSagaStreamClient {
    private final SagaPaymentExecutor executor;
    private PaymentServiceGrpc.PaymentServiceBlockingStub serviceBlockingStub;

    SagaPaymentClient(SagaSdkConfig config, SagaPaymentExecutor executor) throws SagaException {
        super(config);
        this.executor = executor;
        initStub();
    }

    @Override
    void initStub() {
        serviceBlockingStub = PaymentServiceGrpc.newBlockingStub(channel).withCallCredentials(addAuthentication());
        initStreamStub();
        SagaStatusUpdateObserver.getInstance().with(executor);
    }

    public String createCybersourcePaymentMethod(String oauthId,
                                                 String cardType,
                                                 String expirationMonth,
                                                 String expirationYear,
                                                 String instrumentId,
                                                 SagaAddress address) throws SagaException {
        var request = CreatePaymentMethodRequest.newBuilder()
                .setOauthId(oauthId)
                .setCybersource(CreateCybersourceCardProto.newBuilder()
                        .setCardType(cardType)
                        .setExpMonth(expirationMonth)
                        .setExpYear(expirationYear)
                        .setInstrumentId(instrumentId)
                        .setBillingAddress(SagaAddress.toProto(address))
                        .build())
                .build();
        return createPaymentMethod(request);
    }

    public String startUpholdPaymentMethodLink(String oauthId) throws SagaException {
        var request = CreatePaymentMethodRequest.newBuilder()
                .setOauthId(oauthId)
                .setUphold(UpholdStartLinkProto.newBuilder().build())
                .build();
        return createPaymentMethod(request);
    }

    private String createPaymentMethod(CreatePaymentMethodRequest request) throws SagaException {
        try {
            var receivedResponse = serviceBlockingStub.createPaymentMethod(request);
            executor.emitReceived(request.getOauthId(), receivedResponse.getTraceId());
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on createPaymentMethod, payment method may be lost!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public String updateCybersourcePaymentMethod(String oauthId,
                                                 String paymentMethodToken,
                                                 String cardType,
                                                 String expirationMonth,
                                                 String expirationYear,
                                                 String instrumentId,
                                                 SagaAddress address) throws SagaException {
        var request = UpdatePaymentMethodRequest.newBuilder()
                .setOauthId(oauthId)
                .setCybersource(UpdateCybersourceCardProto.newBuilder()
                        .setPaymentMethodToken(paymentMethodToken)
                        .setCardType(cardType)
                        .setExpMonth(expirationMonth)
                        .setExpYear(expirationYear)
                        .setInstrumentId(instrumentId)
                        .setBillingAddress(SagaAddress.toProto(address))
                        .build())
                .build();
        return updatePaymentMethod(request);
    }

    public String finishUpholdPaymentMethodLink(String oauthId,
                                                String stateCode,
                                                String generatedTemporaryCode) throws SagaException {
        var request = UpdatePaymentMethodRequest.newBuilder()
                .setOauthId(oauthId)
                .setUphold(UpholdFinishLinkProto.newBuilder()
                        .setStateCode(stateCode)
                        .setGeneratedTemporaryCode(generatedTemporaryCode)
                        .build())
                .build();
        return updatePaymentMethod(request);
    }

    private String updatePaymentMethod(UpdatePaymentMethodRequest request) throws SagaException {
        try {
            var receivedResponse = serviceBlockingStub.updatePaymentMethod(request);
            executor.emitReceived(request.getOauthId(), receivedResponse.getTraceId());
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on updatePaymentMethod, update may be lost!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public String deletePaymentMethod(String oauthId, String paymentMethodToken, PaymentProviderId providerId) throws SagaException {
        try {
            var request = DeletePaymentMethodRequest.newBuilder()
                    .setOauthId(oauthId)
                    .setPaymentMethodToken(paymentMethodToken)
                    .setPaymentProviderId(providerId)
                    .build();

            var receivedResponse = serviceBlockingStub.deletePaymentMethod(request);
            executor.emitReceived(oauthId, receivedResponse.getTraceId());
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on createOrder, order may be lost!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public Optional<SagaPaymentMethod> getPaymentMethod(String oauthId, PaymentProviderId paymentProviderId) {
        var request = GetPaymentMethodRequest.newBuilder()
                .setOauthId(oauthId)
                .setPaymentProviderId(paymentProviderId)
                .build();

        var paymentMethodProto = serviceBlockingStub.getPaymentMethod(request);
        return Optional.of(SagaPaymentMethod.fromProto(paymentMethodProto));
    }
}

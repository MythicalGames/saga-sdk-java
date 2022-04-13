package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.SagaPaymentExecutor;
import games.mythical.saga.sdk.client.model.SagaPaymentMethod;
import games.mythical.saga.sdk.client.observer.SagaStatusUpdateObserver;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.payment.PaymentServiceGrpc;
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

    public String createPaymentMethod(String oauthId,
                                      PaymentMethodData paymentMethodData,
                                      Address address) throws SagaException {
        try {
            var request = CreatePaymentMethodRequest.newBuilder()
                    .setOauthId(oauthId)
                    .setPaymentMethodData(paymentMethodData)
                    .setAddress(address)
                    .build();

            var receivedResponse = serviceBlockingStub.createPaymentMethod(request);
            executor.emitReceived(oauthId, receivedResponse.getTraceId());
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on createOrder, order may be lost!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public String updatePaymentMethod(String oauthId,
                                      PaymentMethodData paymentMethodData,
                                      Address address) throws SagaException {
        try {
            var request = UpdatePaymentMethodRequest.newBuilder()
                    .setOauthId(oauthId)
                    .setPaymentMethodData(paymentMethodData)
                    .setAddress(address)
                    .build();

            var receivedResponse = serviceBlockingStub.updatePaymentMethod(request);
            executor.emitReceived(oauthId, receivedResponse.getTraceId());
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on createOrder, order may be lost!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public String deletePaymentMethod(String oauthId, PaymentMethodData paymentMethodData) throws SagaException {
        try {
            var request = DeletePaymentMethodRequest.newBuilder()
                    .setOauthId(oauthId)
                    .setPaymentMethodData(paymentMethodData)
                    .build();

            var receivedResponse = serviceBlockingStub.deletePaymentMethod(request);
            executor.emitReceived(oauthId, receivedResponse.getTraceId());
            return receivedResponse.getTraceId();
        } catch (
        StatusRuntimeException e) {
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

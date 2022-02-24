package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.model.IVIToken;
import games.mythical.saga.sdk.exception.IVIException;
import games.mythical.saga.sdk.proto.api.order.PaymentProviderId;
import games.mythical.saga.sdk.proto.api.payment.BraintreeTokenRequest;
import games.mythical.saga.sdk.proto.api.payment.CreateTokenRequest;
import games.mythical.saga.sdk.proto.api.payment.PaymentServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class IVIPaymentClient extends AbstractIVIClient {
    private PaymentServiceGrpc.PaymentServiceBlockingStub serviceBlockingStub;

    @SuppressWarnings("unused")
    public IVIPaymentClient() throws IVIException {
        super();

        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .keepAliveTime(keepAlive, TimeUnit.SECONDS)
                .build();
        initStub();
    }

    IVIPaymentClient(ManagedChannel channel) throws IVIException {
        this.channel = channel;
        initStub();
    }

    @Override
    void initStub() {
        serviceBlockingStub = PaymentServiceGrpc.newBlockingStub(channel).withCallCredentials(addAuthentication());
    }

    public IVIToken getToken(PaymentProviderId providerId, String playerId) throws IVIException {
        var builder = CreateTokenRequest.newBuilder()
                .setEnvironmentId(environmentId);

        if (PaymentProviderId.BRAINTREE.equals(providerId)) {
            builder.setBraintree(BraintreeTokenRequest.newBuilder()
                    .setPlayerId(playerId)
                    .build());
        }

        try {
            var order = serviceBlockingStub.generateClientToken(builder.build());
            return IVIToken.fromProto(order);
        } catch (StatusRuntimeException e) {
            throw IVIException.fromGrpcException(e);
        }
    }
}

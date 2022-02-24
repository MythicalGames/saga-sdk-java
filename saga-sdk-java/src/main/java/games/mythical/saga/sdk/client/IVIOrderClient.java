package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.IVIOrderExecutor;
import games.mythical.saga.sdk.client.model.IVIFinalizeOrderResponse;
import games.mythical.saga.sdk.client.model.IVIOrder;
import games.mythical.saga.sdk.client.model.IVIOrderAddress;
import games.mythical.saga.sdk.client.model.IVIPurchasedItems;
import games.mythical.saga.sdk.client.observer.IVIOrderObserver;
import games.mythical.saga.sdk.exception.IVIErrorCode;
import games.mythical.saga.sdk.exception.IVIException;
import games.mythical.ivi.sdk.proto.api.order.*;
import games.mythical.ivi.sdk.proto.streams.Subscribe;
import games.mythical.ivi.sdk.proto.streams.order.OrderStreamGrpc;
import games.mythical.saga.sdk.util.ConversionUtils;
import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
public class IVIOrderClient extends AbstractIVIClient {
    private OrderServiceGrpc.OrderServiceBlockingStub serviceBlockingStub;
    private final IVIOrderExecutor orderExecutor;

    @SuppressWarnings("unused")
    public IVIOrderClient(IVIOrderExecutor orderExecutor) throws IVIException {
        super();

        this.orderExecutor = orderExecutor;
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .keepAliveTime(keepAlive, TimeUnit.SECONDS)
                .build();
        initStub();
    }

    IVIOrderClient(IVIOrderExecutor orderExecutor, ManagedChannel channel) throws IVIException {
        this.orderExecutor = orderExecutor;
        this.channel = channel;
        initStub();
    }

    @Override
    void initStub() {
        serviceBlockingStub = OrderServiceGrpc.newBlockingStub(channel).withCallCredentials(addAuthentication());
        var streamBlockingStub = OrderStreamGrpc.newBlockingStub(channel)
                .withCallCredentials(addAuthentication());
        subscribeToStream(new IVIOrderObserver(orderExecutor, streamBlockingStub, this::subscribeToStream));
    }

    void subscribeToStream(IVIOrderObserver observer) {
        // set up server stream
        var streamStub = OrderStreamGrpc.newStub(channel).withCallCredentials(addAuthentication());
        var subscribe = Subscribe.newBuilder()
                .setEnvironmentId(environmentId)
                .build();
        streamStub.orderStatusStream(subscribe, observer);
    }

    public Optional<IVIOrder> getOrder(String orderId) throws IVIException {
        var request = GetOrderRequest.newBuilder()
                .setEnvironmentId(environmentId)
                .setOrderId(orderId)
                .build();

        try {
            var order = serviceBlockingStub.getOrder(request);
            return Optional.of(IVIOrder.fromProto(order));
        } catch (StatusRuntimeException e) {
            if (e.getStatus() == Status.NOT_FOUND) {
                return Optional.empty();
            }

            throw IVIException.fromGrpcException(e);
        }
    }

    public IVIOrder createPrimaryOrder(String storeId,
                                       String buyerPlayerId,
                                       BigDecimal subTotal,
                                       IVIOrderAddress address,
                                       PaymentProviderId paymentProviderId,
                                       Collection<IVIPurchasedItems> purchasedItems,
                                       Map<String, Object> metadata,
                                       String requestIp) throws IVIException {
        var purchaseItemsProtos = new ArrayList<ItemTypeOrder>();
        for(var purchasedItem : purchasedItems ) {
            purchaseItemsProtos.add(purchasedItem.toProto());
        }

        var builder = CreateOrderRequest.newBuilder()
                .setEnvironmentId(environmentId)
                .setStoreId(storeId)
                .setBuyerPlayerId(buyerPlayerId)
                .setSubTotal(subTotal.toString())
                .setAddress(address.toProto())
                .setPaymentProviderId(paymentProviderId)
                .setPurchasedItems(ItemTypeOrders.newBuilder()
                        .addAllPurchasedItems(purchaseItemsProtos)
                        .build());

        if (metadata != null) {
            builder.setMetadata(ConversionUtils.convertProperties(metadata));
        }

        if (StringUtils.isNotBlank(requestIp)) {
            builder.setRequestIp(requestIp);
        }

        return createOrder(builder.build());
    }

    private IVIOrder createOrder(CreateOrderRequest request) throws IVIException {
        try {
            var result = serviceBlockingStub.createOrder(request);
            orderExecutor.updateOrder(result.getOrderId(), result.getOrderStatus());
            return IVIOrder.fromProto(result);
        } catch (StatusRuntimeException e) {
            throw IVIException.fromGrpcException(e);
        } catch (StatusException e) {
            throw IVIException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling updateOrder on createOrder, order will be in an invalid state!", e);
            throw new IVIException(IVIErrorCode.LOCAL_EXCEPTION);
        }
    }

    public IVIFinalizeOrderResponse finalizeBraintreeOrder(String orderId,
                                                           String clientToken,
                                                           String paymentNonce,
                                                           String fraudSessionId) throws IVIException {
        var paymentData = PaymentRequestProto.newBuilder()
                .setBraintree(BraintreePaymentRequestProto.newBuilder()
                        .setBraintreeClientToken(clientToken)
                        .setBraintreePaymentNonce(paymentNonce)
                        .build())
                .build();

        return finalizeOrder(orderId, paymentData, fraudSessionId);
    }

    public IVIFinalizeOrderResponse finalizeBitpayOrder(String orderId,
                                                        String invoiceId,
                                                        String fraudSessionId) throws IVIException {
        var paymentData = PaymentRequestProto.newBuilder()
                .setBitpay(BitPayPaymentRequestProto.newBuilder()
                        .setInvoiceId(invoiceId)
                        .build())
                .build();

        return finalizeOrder(orderId, paymentData, fraudSessionId);
    }

    private IVIFinalizeOrderResponse finalizeOrder(String orderId, PaymentRequestProto paymentData, String fraudSessionId) throws IVIException {
        var builder = FinalizeOrderRequest.newBuilder()
                .setEnvironmentId(environmentId)
                .setOrderId(orderId)
                .setPaymentRequestData(paymentData);

        if (StringUtils.isNotBlank(fraudSessionId)) {
            builder.setFraudSessionId(fraudSessionId);
        }

        var request = builder.build();
        try {
            var result = serviceBlockingStub.finalizeOrder(request);
            orderExecutor.updateOrder(orderId, result.getOrderStatus());
            return IVIFinalizeOrderResponse.fromProto(result);
        } catch (StatusRuntimeException e) {
            throw IVIException.fromGrpcException(e);
        } catch (StatusException e) {
            throw IVIException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling updateOrder on createOrder, order will be in an invalid state!", e);
            throw new IVIException(IVIErrorCode.LOCAL_EXCEPTION);
        }
    }
}

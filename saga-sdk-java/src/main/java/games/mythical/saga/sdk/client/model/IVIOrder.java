package games.mythical.saga.sdk.client.model;

import games.mythical.saga.sdk.exception.IVIException;
import games.mythical.ivi.sdk.proto.api.order.Order;
import games.mythical.ivi.sdk.proto.api.order.PaymentProviderId;
import games.mythical.ivi.sdk.proto.common.order.OrderState;
import games.mythical.saga.sdk.util.ConversionUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;

@Slf4j
@Getter
public class IVIOrder {
    private final String orderId;
    private final String storeId;
    private final String buyerPlayerId;
    private final BigDecimal tax;
    private final BigDecimal total;
    private final IVIOrderAddress address;
    private final PaymentProviderId paymentProviderId;
    private final Map<String, Object> metadata;
    private final String createdBy;
    private final String requestIp;
    private final String environmentId;
    private final OrderState orderStatus;
    private final Instant createdTimestamp;
    private final Map<String, Object> bitpayInvoice;

    IVIOrder(Order order) throws IVIException {
        orderId = order.getOrderId();
        storeId = order.getStoreId();
        buyerPlayerId = order.getBuyerPlayerId();
        tax = new BigDecimal(order.getTax());
        total = new BigDecimal(order.getTotal());
        address = IVIOrderAddress.fromProto(order.getAddress());
        paymentProviderId = order.getPaymentProviderId();
        metadata = ConversionUtils.convertProperties(order.getMetadata());
        createdBy = order.getCreatedBy();
        requestIp = order.getRequestIp();
        environmentId = order.getEnvironmentId();
        orderStatus = order.getOrderStatus();
        createdTimestamp = Instant.ofEpochSecond(order.getCreatedTimestamp());

        if (order.hasPaymentProviderData()) {
            bitpayInvoice = ConversionUtils.convertProperties(order.getPaymentProviderData().getBitpay().getInvoice());
        } else {
            bitpayInvoice = Collections.emptyMap();
        }
    }

    public static IVIOrder fromProto(Order order) throws IVIException {
        return new IVIOrder(order);
    }
}

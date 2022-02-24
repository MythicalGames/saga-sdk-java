package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.MockOrderExecutor;
import games.mythical.saga.sdk.client.model.IVIOrder;
import games.mythical.saga.sdk.client.model.IVIOrderAddress;
import games.mythical.saga.sdk.client.model.IVIPurchasedItems;
import games.mythical.saga.sdk.exception.IVIErrorCode;
import games.mythical.saga.sdk.exception.IVIException;
import games.mythical.ivi.sdk.proto.api.order.PaymentProviderId;
import games.mythical.ivi.sdk.proto.common.order.OrderState;
import games.mythical.saga.sdk.server.order.MockOrderServer;
import games.mythical.saga.sdk.server.order.MockOrderServiceImpl;
import games.mythical.saga.sdk.util.ConcurrentFinisher;
import io.grpc.ManagedChannelBuilder;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class IVIOrderClientTest extends AbstractClientTest {
    private MockOrderServer orderServer;
    private MockOrderExecutor orderExecutor;
    private IVIOrderClient orderClient;
    private Map<String, IVIOrder> orders;

    @BeforeEach
    void setUp() throws Exception {
        orderServer = new MockOrderServer();
        orderServer.start();
        port = orderServer.getPort();
        setUpConfig();

        orders = generateOrders(3);
        orderServer.getOrderService().setOrders(orders.values());

        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();

        orderExecutor = MockOrderExecutor.builder().build();
        orderClient = new IVIOrderClient(orderExecutor, channel);
    }

    @AfterEach
    void tearDown() {
        orderServer.stop();
    }

    @Test
    void getOrder() throws Exception {
        var orderId = orders.keySet().iterator().next();

        var order = orderClient.getOrder(orderId);
        assertTrue(order.isPresent());
        verifyOrder(orders.get(orderId), order.get());


        orderId = RandomStringUtils.randomAlphanumeric(30);
        order = orderClient.getOrder(orderId);
        assertTrue(order.isEmpty());

        orderServer.verifyCalls("GetOrder", 2);
    }

    @Test
    void createPrimaryOrder() throws Exception {
        var storeId = RandomStringUtils.randomAlphanumeric(30);
        var playerId = RandomStringUtils.randomAlphanumeric(30);
        var subTotal = BigDecimal.valueOf(RandomUtils.nextDouble(0, 100));
        var address = generateAddress();
        var providerId = PaymentProviderId.BRAINTREE;
        var purchasedItems = generatePurchasedItems(3);

        var order = orderClient.createPrimaryOrder(storeId, playerId, subTotal, address, providerId, purchasedItems, null, null);

        orderServer.verifyCalls("CreateOrder", 1);
        assertNotNull(orderExecutor.getOrderId());
        assertEquals(OrderState.STARTED, orderExecutor.getOrderStatus());

        var orderId = orderExecutor.getOrderId();
        assertEquals(order.getOrderId(), orderId);
        ConcurrentFinisher.start(orderId);

        orderServer.getOrderStream().sendStatus(environmentId, orderId, OrderState.PROCESSING);

        ConcurrentFinisher.wait(orderId);

        orderServer.verifyCalls("OrderStatusStream", 1);
        orderServer.verifyCalls("OrderStatusConfirmation", 1);

        assertEquals(orderId, orderExecutor.getOrderId());
        assertEquals(OrderState.PROCESSING, orderExecutor.getOrderStatus());

        var orderOpt = orderClient.getOrder(orderId);

        assertTrue(orderOpt.isPresent());
        assertEquals(orderId, orderOpt.get().getOrderId());
        assertEquals(OrderState.PROCESSING, orderOpt.get().getOrderStatus());

        orderServer.verifyCalls("GetOrder", 1);
    }

    @Test
    void finalizeBraintreeOrder() throws Exception {
        var orderId = orders.keySet().iterator().next();
        var clientToken = RandomStringUtils.randomAlphanumeric(30);
        var paymentNonce = RandomStringUtils.randomAlphanumeric(30);
        var fraudSessionId = RandomStringUtils.randomAlphanumeric(30);

        orderExecutor.setOrderId(orders.get(orderId).getOrderId());
        orderExecutor.setOrderStatus(orders.get(orderId).getOrderStatus());

        var finalizeResponse = orderClient.finalizeBraintreeOrder(orderId, clientToken, paymentNonce, fraudSessionId);

        assertTrue(finalizeResponse.isSuccess());
        assertNotNull(finalizeResponse.getFraudScore());
        assertEquals(orderId, orderExecutor.getOrderId());
        assertEquals(OrderState.PROCESSING, orderExecutor.getOrderStatus());

        orderServer.verifyCalls("FinalizeOrder", 1);

        ConcurrentFinisher.start(orderId);

        orderServer.getOrderStream().sendStatus(environmentId, orderId, OrderState.COMPLETE);

        ConcurrentFinisher.wait(orderId);

        assertEquals(orderId, orderExecutor.getOrderId());
        assertEquals(OrderState.COMPLETE, orderExecutor.getOrderStatus());

        orderServer.verifyCalls("OrderStatusStream", 1);
        orderServer.verifyCalls("OrderStatusConfirmation", 1);
    }

    @Test
    void finalizeBraintreeOrderAndUnexpectedError() {
        var orderId = orders.keySet().iterator().next();
        var clientToken = RandomStringUtils.randomAlphanumeric(30);
        var paymentNonce = RandomStringUtils.randomAlphanumeric(30);
        var ex = assertThrows(IVIException.class, () -> orderClient.finalizeBraintreeOrder(orderId, clientToken,
                paymentNonce, MockOrderServiceImpl.FAILURE_TRIGGER));

        Assertions.assertEquals(IVIErrorCode.NOT_FOUND, ex.getCode());
        assertEquals("FAILED_PRECONDITION: " + MockOrderServiceImpl.FAILURE_MESSAGE, ex.getMessage());
    }

    @Test
    void finalizeBitpayOrder() throws Exception {
        var orderId = orders.keySet().iterator().next();
        var invoiceId = RandomStringUtils.randomAlphanumeric(30);

        orderExecutor.setOrderId(orders.get(orderId).getOrderId());
        orderExecutor.setOrderStatus(orders.get(orderId).getOrderStatus());

        var finalizeResponse = orderClient.finalizeBitpayOrder(orderId, invoiceId, null);

        assertTrue(finalizeResponse.isSuccess());
        assertNull(finalizeResponse.getFraudScore());
        assertEquals(orderId, orderExecutor.getOrderId());
        assertEquals(OrderState.PROCESSING, orderExecutor.getOrderStatus());

        orderServer.verifyCalls("FinalizeOrder", 1);

        ConcurrentFinisher.start(orderId);

        orderServer.getOrderStream().sendStatus(environmentId, orderId, OrderState.COMPLETE);

        ConcurrentFinisher.wait(orderId);

        assertEquals(orderId, orderExecutor.getOrderId());
        assertEquals(OrderState.COMPLETE, orderExecutor.getOrderStatus());

        orderServer.verifyCalls("OrderStatusStream", 1);
        orderServer.verifyCalls("OrderStatusConfirmation", 1);
    }

    @Test
    void finalizeOrderNotFound() {
        var orderId = RandomStringUtils.randomAlphanumeric(30);
        var clientToken = RandomStringUtils.randomAlphanumeric(30);
        var paymentNonce = RandomStringUtils.randomAlphanumeric(30);

        try {
            orderClient.finalizeBraintreeOrder(orderId, clientToken, paymentNonce, null);
            fail("Didn't return an expected IVIException!");
        } catch (IVIException e) {
            assertEquals(IVIErrorCode.NOT_FOUND, e.getCode());
        }
    }

    void verifyOrder(IVIOrder expectedOrder, IVIOrder actualOrder) {
        assertEquals(expectedOrder.getOrderId(), actualOrder.getOrderId());
        assertEquals(expectedOrder.getStoreId(), actualOrder.getStoreId());
        assertEquals(expectedOrder.getBuyerPlayerId(), actualOrder.getBuyerPlayerId());
        assertEquals(expectedOrder.getTax(), actualOrder.getTax());
        assertEquals(expectedOrder.getTotal(), actualOrder.getTotal());
        assertEquals(expectedOrder.getPaymentProviderId(), actualOrder.getPaymentProviderId());
        assertEquals(expectedOrder.getOrderStatus(), actualOrder.getOrderStatus());
        verifyAddress(expectedOrder.getAddress(), actualOrder.getAddress());
    }

    void verifyAddress(IVIOrderAddress expectedAddress, IVIOrderAddress actualAddress) {
        assertEquals(expectedAddress.getFirstName(), actualAddress.getFirstName());
        assertEquals(expectedAddress.getLastName(), actualAddress.getLastName());
        assertEquals(expectedAddress.getAddressLine1(), actualAddress.getAddressLine1());
        assertEquals(expectedAddress.getAddressLine2(), actualAddress.getAddressLine2());
        assertEquals(expectedAddress.getCity(), actualAddress.getCity());
        assertEquals(expectedAddress.getState(), actualAddress.getState());
        assertEquals(expectedAddress.getCountryName(), actualAddress.getCountryName());
        assertEquals(expectedAddress.getPostalCode(), actualAddress.getPostalCode());
        assertEquals(expectedAddress.getCountryIsoAlpha2(), actualAddress.getCountryIsoAlpha2());
    }

    void verifyPurchaseItem(IVIPurchasedItems expectedItem, IVIPurchasedItems actualItem) {
        assertEquals(expectedItem.getGameInventoryIds(), actualItem.getGameInventoryIds());
        assertEquals(expectedItem.getItemName(), actualItem.getItemName());
        assertEquals(expectedItem.getGameItemTypeId(), actualItem.getGameItemTypeId());
        assertEquals(expectedItem.getAmountPaid(), actualItem.getAmountPaid());
        assertEquals(expectedItem.getCurrency(), actualItem.getCurrency());

        verifyMetadata(expectedItem.getMetadata(), actualItem.getMetadata());
    }
}
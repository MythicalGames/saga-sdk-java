package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.MockMythTokenExecutor;
import games.mythical.saga.sdk.proto.api.myth.*;
import games.mythical.saga.sdk.proto.api.payments.CardPaymentData;
import games.mythical.saga.sdk.proto.api.payments.CybersourcePaymentData;
import games.mythical.saga.sdk.proto.common.ReceivedResponse;
import games.mythical.saga.sdk.proto.common.myth.MythTokenState;
import games.mythical.saga.sdk.server.myth.MockMythTokenServer;
import games.mythical.saga.sdk.util.ConcurrentFinisher;
import io.grpc.ManagedChannelBuilder;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SagaMythTokenClientTest extends AbstractClientTest {

    private final MockMythTokenExecutor executor = MockMythTokenExecutor.builder().build();
    private SagaMythTokenClient mythTokenClient;
    private MockMythTokenServer mythTokenServer;


    private final CybersourcePaymentData CYBERSOURCE_DATA = CybersourcePaymentData.newBuilder()
            .setCardType("VISA")
            .build();
    private final CardPaymentData CARD_PAYMENT_DATA = CardPaymentData.newBuilder()
            .setCybersource(CYBERSOURCE_DATA)
            .build();

    private final String QUOTE_ID = "my-quote=1234";
    private final String USER_ID = "user-1234";

    @Mock
    private MythServiceGrpc.MythServiceBlockingStub mockServiceBlockingStub;

    @BeforeEach
    void setup() throws Exception {
        mythTokenServer = new MockMythTokenServer();
        mythTokenServer.start();
        port = mythTokenServer.getPort();
        setUpConfig();

        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();

        mythTokenClient = new SagaMythTokenClient(executor, channel);
        // mocking the service blocking stub clients are connected to
        FieldUtils.writeField(mythTokenClient, "serviceBlockingStub", mockServiceBlockingStub, true);
    }

    @Test
    public void getGasFee() throws Exception {
        var expectedResponse = GasFeeProto.newBuilder()
                .setGweiAmount("100000000000")
                .setConvertedAmount("1234")
                .setEthAmount("1")
                .setConvertedCurrency("USD")
                .build();

        when(mockServiceBlockingStub.getGasFee(any())).thenReturn(expectedResponse);
        var gasFeeResponse = mythTokenClient.getGasFee();

        assertTrue(gasFeeResponse.isPresent());
        var gasFee = gasFeeResponse.get();
        compareObjectsByReflection(expectedResponse, gasFee, null);
    }

    @Test
    public void getCurrencyExchange() throws Exception {
        var expectedResponse = CurrencyExchangeProto.newBuilder()
                .setAsk("1.12345")
                .setBid("5.4321")
                .build();

        when(mockServiceBlockingStub.getCurrencyExchange(any())).thenReturn(expectedResponse);
        var currencyExchangeResponse = mythTokenClient.getExchangeRate();

        assertTrue(currencyExchangeResponse.isPresent());
        var currencyExchange = currencyExchangeResponse.get();
        compareObjectsByReflection(expectedResponse, currencyExchange, null);
    }

    @Test
    public void quoteBuyingMythToken() {
        var expectedResponse = QuoteBuyingMythTokenResponse.newBuilder()
                .setUpholdQuoteId(QUOTE_ID)
                .setOriginSubAccount("this_account")
                .build();
        when(mockServiceBlockingStub.quoteBuyingMythToken(any())).thenReturn(expectedResponse);
        var quoteBuyingMythTokenResponse = mythTokenClient.quoteBuyingMythToken(
                new BigDecimal("1.1234"),
                CARD_PAYMENT_DATA,
                "USD",
                "this_account",
                USER_ID);

        assertTrue(quoteBuyingMythTokenResponse.isPresent());
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    public void confirmBuyingMythToken() throws Exception {
        var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.confirmBuyingMythToken(any())).thenReturn(expectedResponse);
        mythTokenClient.confirmBuyingMythToken(QUOTE_ID, USER_ID, CARD_PAYMENT_DATA);

        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertNotEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        // a short wait is needed so the status stream can be hooked up
        // before the emitting the event from the sendStatus method
        Thread.sleep(500);
        ConcurrentFinisher.start(executor.getTraceId());

        mythTokenServer.getMythTokenStream().sendStatus(environmentId, MythTokenProto.newBuilder()
                .setTraceId(executor.getTraceId())
                .setQuoteId(QUOTE_ID)
                .build(), MythTokenState.TRANSFERRED);

        ConcurrentFinisher.wait(executor.getTraceId());
        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(MythTokenState.TRANSFERRED, executor.getTokenState());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        mythTokenServer.verifyCalls("MythTokenStatusStream", 1);
        mythTokenServer.verifyCalls("MythTokenStatusConfirmation", 1);
    }

    @Test
    public void quoteMythTokenWithdrawal() {
        var expectedResponse = QuoteMythTokenWithdrawalResponse.newBuilder()
                .setGasFee("1.1234")
                .setTotalAmount("3000")
                .build();

        when(mockServiceBlockingStub.quoteMythTokenWithdrawal(any())).thenReturn(expectedResponse);
        var response = mythTokenClient.quoteMythTokenWithdrawal(USER_ID, new BigDecimal("3000"));
        assertTrue(response.isPresent());
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    public void confirmMythTokenWithdrawal() throws Exception {
        var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.confirmMythTokenWithdrawal(any())).thenReturn(expectedResponse);
        mythTokenClient.confirmMythTokenWithdrawal(QUOTE_ID);

        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertNotEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        // a short wait is needed so the status stream can be hooked up
        // before the emitting the event from the sendStatus method
        Thread.sleep(500);
        ConcurrentFinisher.start(executor.getTraceId());

        mythTokenServer.getMythTokenStream().sendStatus(environmentId, MythTokenProto.newBuilder()
                .setTraceId(executor.getTraceId())
                .setQuoteId(QUOTE_ID)
                .build(), MythTokenState.TRANSFERRED);

        ConcurrentFinisher.wait(executor.getTraceId());
        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(MythTokenState.TRANSFERRED, executor.getTokenState());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        mythTokenServer.verifyCalls("MythTokenStatusStream", 1);
        mythTokenServer.verifyCalls("MythTokenStatusConfirmation", 1);
    }
}

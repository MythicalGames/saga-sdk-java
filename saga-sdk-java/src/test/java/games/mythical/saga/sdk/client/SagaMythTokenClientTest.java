package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.MockMythTokenExecutor;
import games.mythical.saga.sdk.client.model.SagaCreditCardData;
import games.mythical.saga.sdk.proto.api.myth.*;
import games.mythical.saga.sdk.proto.common.ReceivedResponse;
import games.mythical.saga.sdk.proto.common.myth.MythTokenState;
import games.mythical.saga.sdk.proto.streams.StatusUpdate;
import games.mythical.saga.sdk.proto.streams.myth.MythTokenStatusUpdate;
import games.mythical.saga.sdk.proto.streams.myth.MythTokenUpdate;
import games.mythical.saga.sdk.server.MockServer;
import games.mythical.saga.sdk.server.stream.MockStatusStreamingImpl;
import games.mythical.saga.sdk.util.ConcurrentFinisher;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.AfterEach;
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

    private final SagaCreditCardData CREDIT_CARD_DATA = SagaCreditCardData.builder()
        .cardType("VISA")
        .build();
    private final String QUOTE_ID = "my-quote=1234";
    private final String USER_ID = "user-1234";
    private SagaMythTokenClient mythTokenClient;
    private MockServer mythTokenServer;
    @Mock
    private MythServiceGrpc.MythServiceBlockingStub mockServiceBlockingStub;
    private final String DEPOSIT_SOURCE = "deposit_source";
    private final String  DEPOSIT_DESTINATION = "deposit_destination";
    private final int DEPOSIT_QUANTITY = 1;
    private final String DEPOSIT_CARD_ID = "deposit_card_id";

    @BeforeEach
    void setup() throws Exception {
        mythTokenServer = new MockServer(new MockStatusStreamingImpl());
        mythTokenServer.start();
        port = mythTokenServer.getPort();

        mythTokenClient = setUpFactory().createSagaMythTokenClient(executor);
        // mocking the service blocking stub clients are connected to
        FieldUtils.writeField(mythTokenClient, "serviceBlockingStub", mockServiceBlockingStub, true);
    }

    @AfterEach
    void tearDown() throws Exception {
        mythTokenClient.stop();
        // client shutdown is not immediate
        Thread.sleep(500);

        mythTokenServer.stop();
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

        assertNotNull(gasFeeResponse);
        compareObjectsByReflection(expectedResponse, gasFeeResponse, null);
    }

    @Test
    public void getCurrencyExchange() throws Exception {
        var expectedResponse = CurrencyExchangeProto.newBuilder()
                .setAsk("1.12345")
                .setBid("5.4321")
                .build();

        when(mockServiceBlockingStub.getCurrencyExchange(any())).thenReturn(expectedResponse);
        var currencyExchangeResponse = mythTokenClient.getExchangeRate();

        assertNotNull(currencyExchangeResponse);
        compareObjectsByReflection(expectedResponse, currencyExchangeResponse, null);
    }

    @Test
    public void quoteBuyingMythToken() throws Exception {
        var expectedResponse = QuoteBuyingMythTokenResponse.newBuilder()
                .setUpholdQuoteId(QUOTE_ID)
                .setOriginSubAccount("this_account")
                .build();
        when(mockServiceBlockingStub.quoteBuyingMythToken(any())).thenReturn(expectedResponse);
        var quoteBuyingMythTokenResponse = mythTokenClient.quoteBuyingMythToken(
                new BigDecimal("1.1234"),
                CREDIT_CARD_DATA,
                "USD",
                "this_account",
                USER_ID);

        assertNotNull(quoteBuyingMythTokenResponse);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    public void confirmBuyingMythToken() throws Exception {
        var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.confirmBuyingMythToken(any())).thenReturn(expectedResponse);
        final var traceId = mythTokenClient.confirmBuyingMythToken(QUOTE_ID, USER_ID, CREDIT_CARD_DATA);
        checkTraceAndStart(expectedResponse, traceId);

        final var update = MythTokenStatusUpdate.newBuilder()
                .setTokenState(MythTokenState.TRANSFERRED);
        mythTokenServer.getStatusStream().sendStatus(config.getTitleId(), StatusUpdate.newBuilder()
                .setTraceId(traceId)
                .setMythTokenUpdate(MythTokenUpdate.newBuilder().setStatusUpdate(update))
                .build());

        ConcurrentFinisher.wait(traceId);
        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(MythTokenState.TRANSFERRED, executor.getTokenState());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        mythTokenServer.verifyCalls("StatusStream", 1);
        mythTokenServer.verifyCalls("StatusConfirmation", 1);
    }

    @Test
    public void quoteMythTokenWithdrawal() throws Exception {
        var expectedResponse = QuoteMythTokenWithdrawalResponse.newBuilder()
                .setGasFee("1.1234")
                .setTotalAmount("3000")
                .build();

        when(mockServiceBlockingStub.quoteMythTokenWithdrawal(any())).thenReturn(expectedResponse);
        var response = mythTokenClient.quoteMythTokenWithdrawal(USER_ID, new BigDecimal("3000"));
        assertNotNull(response);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    public void confirmMythTokenWithdrawal() throws Exception {
        var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.confirmMythTokenWithdrawal(any())).thenReturn(expectedResponse);
        final var traceId = mythTokenClient.confirmMythTokenWithdrawal(QUOTE_ID);
        checkTraceAndStart(expectedResponse, traceId);

        final var update = MythTokenStatusUpdate.newBuilder()
                .setTokenState(MythTokenState.WITHDRAWN);
        mythTokenServer.getStatusStream().sendStatus(config.getTitleId(), StatusUpdate.newBuilder()
                .setTraceId(traceId)
                .setMythTokenUpdate(MythTokenUpdate.newBuilder().setStatusUpdate(update))
                .build());

        ConcurrentFinisher.wait(traceId);
        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(MythTokenState.WITHDRAWN, executor.getTokenState());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        mythTokenServer.verifyCalls("StatusStream", 1);
        mythTokenServer.verifyCalls("StatusConfirmation", 1);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    public void depositNmyth() throws Exception {
        var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.confirmMythTokenWithdrawal(any())).thenReturn(expectedResponse);
        final var traceId = mythTokenClient.depositNmyth(
                DEPOSIT_SOURCE, DEPOSIT_DESTINATION, DEPOSIT_QUANTITY, DEPOSIT_CARD_ID);
        checkTraceAndStart(expectedResponse, traceId);

        final var update = MythTokenStatusUpdate.newBuilder()
                .setTokenState(MythTokenState.DEPOSIT);
        mythTokenServer.getStatusStream().sendStatus(config.getTitleId(), StatusUpdate.newBuilder()
                .setTraceId(traceId)
                .setMythTokenUpdate(MythTokenUpdate.newBuilder().setStatusUpdate(update))
                .build());

        ConcurrentFinisher.wait(traceId);
        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(MythTokenState.DEPOSIT, executor.getTokenState());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        mythTokenServer.verifyCalls("StatusStream", 1);
        mythTokenServer.verifyCalls("StatusConfirmation", 1);
    }
}

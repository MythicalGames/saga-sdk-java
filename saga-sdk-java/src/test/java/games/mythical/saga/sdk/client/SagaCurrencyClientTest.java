package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.MockCurrencyExecutor;
import games.mythical.saga.sdk.proto.api.currency.CurrencyProto;
import games.mythical.saga.sdk.proto.api.currency.CurrencyServiceGrpc;
import games.mythical.saga.sdk.proto.common.ReceivedResponse;
import games.mythical.saga.sdk.proto.common.currency.CurrencyState;
import games.mythical.saga.sdk.proto.streams.StatusUpdate;
import games.mythical.saga.sdk.proto.streams.currency.CurrencyStatusUpdate;
import games.mythical.saga.sdk.proto.streams.currency.CurrencyUpdate;
import games.mythical.saga.sdk.server.MockServer;
import games.mythical.saga.sdk.server.stream.MockStatusStreamingImpl;
import games.mythical.saga.sdk.util.ConcurrentFinisher;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SagaCurrencyClientTest extends AbstractClientTest {
    private static final String CURRENCY_ID = RandomStringUtils.randomAlphanumeric(30);
    private static final String OAUTH_ID = RandomStringUtils.randomAlphanumeric(30);
    private static final String OWNER_ADDRESS = RandomStringUtils.randomAlphanumeric(30);

    private final MockCurrencyExecutor executor = MockCurrencyExecutor.builder().build();
    private MockServer currencyServer;
    private SagaCurrencyClient currencyClient;

    @Mock
    private CurrencyServiceGrpc.CurrencyServiceBlockingStub mockServiceBlockingStub;

    @BeforeEach
    void setUp() throws Exception {
        currencyServer = new MockServer(new MockStatusStreamingImpl());
        currencyServer.start();
        port = currencyServer.getPort();

        currencyClient = setUpFactory().createSagaCurrencyClient(executor);
        // mocking the service blocking stub clients are connected to
        FieldUtils.writeField(currencyClient, "serviceBlockingStub", mockServiceBlockingStub, true);
    }

    @AfterEach
    void tearDown() throws Exception {
        currencyClient.stop();
        // client shutdown is not immediate
        Thread.sleep(500);
        currencyServer.stop();
    }

    @Test
    public void getCurrency() throws Exception {
        var expectedResponse = CurrencyProto.newBuilder()
                .setGameCurrencyTypeId(CURRENCY_ID)
                .setQuantity(Integer.toString(RandomUtils.nextInt(0, 1000)))
                .setOwnerAddress(OWNER_ADDRESS)
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.getCurrencyByPlayer(any())).thenReturn(expectedResponse);
        var currencyResponse = currencyClient.getCurrency(CURRENCY_ID, OAUTH_ID);

        assertTrue(currencyResponse.isPresent());
        var currency = currencyResponse.get();
        assertEquals(CURRENCY_ID, currency.getGameCurrencyTypeId());
        assertEquals(expectedResponse.getOwnerAddress(), currency.getOwnerAddress());
        assertEquals(expectedResponse.getQuantity(), currency.getQuantity().toString());

        when(mockServiceBlockingStub.getCurrencyByPlayer(any())).thenThrow(new StatusRuntimeException(Status.NOT_FOUND));
        currencyResponse = currencyClient.getCurrency("INVALID-CURRENCY-ID", "INVALID-USER");

        assertTrue(currencyResponse.isEmpty());
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    public void issueCurrency() throws Exception {
        final var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.issueCurrency(any())).thenReturn(expectedResponse);
        final var traceId = currencyClient.issueCurrency(
                CURRENCY_ID,
                OWNER_ADDRESS,
                Integer.toString(RandomUtils.nextInt(1, 1000)),
                1000L,
                "PAYMENT_TOKEN");
        checkTraceAndStart(expectedResponse, traceId);

        final var update = CurrencyStatusUpdate.newBuilder()
                .setOwnerAddress(OWNER_ADDRESS)
                .setGameCurrencyTypeId(CURRENCY_ID)
                .setCurrencyState(CurrencyState.ISSUED);
        currencyServer.getStatusStream().sendStatus(titleId, StatusUpdate.newBuilder()
                .setTraceId(traceId)
                .setCurrencyUpdate(CurrencyUpdate.newBuilder().setStatusUpdate(update))
                .build());

        ConcurrentFinisher.wait(traceId);

        assertEquals(OWNER_ADDRESS, executor.getOwnerAddress());
        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(CurrencyState.ISSUED, executor.getCurrencyState());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        currencyServer.verifyCalls("StatusStream", 1);
        currencyServer.verifyCalls("StatusConfirmation", 1);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    public void transferCurrency() throws Exception {
        final var SOURCE = RandomStringUtils.randomAlphanumeric(30);
        final var DEST = RandomStringUtils.randomAlphanumeric(30);

        final var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.transferCurrency(any())).thenReturn(expectedResponse);
        final var traceId = currencyClient.transferCurrency(CURRENCY_ID, SOURCE, DEST, Integer.toString(RandomUtils.nextInt(1, 1000)));
        checkTraceAndStart(expectedResponse, traceId);

        final var update = CurrencyStatusUpdate.newBuilder()
                .setOwnerAddress(DEST)
                .setGameCurrencyTypeId(CURRENCY_ID)
                .setCurrencyState(CurrencyState.TRANSFERRED);
        currencyServer.getStatusStream().sendStatus(titleId, StatusUpdate.newBuilder()
                .setTraceId(traceId)
                .setCurrencyUpdate(CurrencyUpdate.newBuilder().setStatusUpdate(update))
                .build());

        ConcurrentFinisher.wait(traceId);

        assertEquals(DEST, executor.getOwnerAddress());
        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(CurrencyState.TRANSFERRED, executor.getCurrencyState());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        currencyServer.verifyCalls("StatusStream", 1);
        currencyServer.verifyCalls("StatusConfirmation", 1);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    public void burnCurrency() throws Exception {
        final var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.burnCurrency(any())).thenReturn(expectedResponse);
        final var traceId = currencyClient.burnCurrency(CURRENCY_ID, OWNER_ADDRESS, Integer.toString(RandomUtils.nextInt(1, 1000)));
        checkTraceAndStart(expectedResponse, traceId);

        final var update = CurrencyStatusUpdate.newBuilder()
                .setOwnerAddress(OWNER_ADDRESS)
                .setGameCurrencyTypeId(CURRENCY_ID)
                .setCurrencyState(CurrencyState.BURNED);
        currencyServer.getStatusStream().sendStatus(titleId, StatusUpdate.newBuilder()
                .setTraceId(traceId)
                .setCurrencyUpdate(CurrencyUpdate.newBuilder().setStatusUpdate(update))
                .build());

        ConcurrentFinisher.wait(traceId);

        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(CurrencyState.BURNED, executor.getCurrencyState());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        currencyServer.verifyCalls("StatusStream", 1);
        currencyServer.verifyCalls("StatusConfirmation", 1);
    }
}
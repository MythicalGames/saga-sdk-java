package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.MockOfferExecutor;
import games.mythical.saga.sdk.proto.api.offer.OfferProto;
import games.mythical.saga.sdk.proto.api.offer.OfferServiceGrpc;
import games.mythical.saga.sdk.proto.api.offer.OffersProto;
import games.mythical.saga.sdk.proto.common.ReceivedResponse;
import games.mythical.saga.sdk.proto.common.offer.OfferState;
import games.mythical.saga.sdk.proto.streams.StatusUpdate;
import games.mythical.saga.sdk.proto.streams.offer.OfferStatusUpdate;
import games.mythical.saga.sdk.proto.streams.offer.OfferUpdate;
import games.mythical.saga.sdk.server.MockServer;
import games.mythical.saga.sdk.server.stream.MockStatusStreamingImpl;
import games.mythical.saga.sdk.util.ConcurrentFinisher;
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

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SagaOfferClientTest extends AbstractClientTest {
    private static final String OAUTH_ID = UUID.randomUUID().toString();
    private static final String CURRENCY = "USD";

    private final MockOfferExecutor executor = MockOfferExecutor.builder().build();
    private MockServer offerServer;
    private SagaOfferClient offerClient;

    @Mock
    private OfferServiceGrpc.OfferServiceBlockingStub mockServiceBlockingStub;

    @BeforeEach
    void setUp() throws Exception {
        offerServer = new MockServer(new MockStatusStreamingImpl());
        offerServer.start();
        port = offerServer.getPort();

        offerClient = setUpFactory().createSagaOfferClient(executor);
        // mocking the service blocking stub clients are connected to
        FieldUtils.writeField(offerClient, "serviceBlockingStub", mockServiceBlockingStub, true);
    }

    @AfterEach
    void tearDown() throws Exception {
        offerClient.stop();
        // client shutdown is not immediate
        Thread.sleep(500);

        offerServer.stop();
    }

    @Test
    public void createOfferQuote() throws Exception {
        final var OFFER_ID = RandomStringUtils.randomAlphanumeric(30);
        final var expectedResponse = genReceived();
        when(mockServiceBlockingStub.createOfferQuote(any())).thenReturn(expectedResponse);

        final var trace = offerClient.createOfferQuote(
                OAUTH_ID,
                RandomStringUtils.randomAlphanumeric(30),
                BigDecimal.TEN,
                CURRENCY
        );
        checkTraceAndStart(expectedResponse, executor, trace);

        final var update = OfferStatusUpdate.newBuilder()
            .setOauthId(OAUTH_ID)
            .setQuoteId(OFFER_ID)
            .setOfferId(OFFER_ID)
            .setTotal(String.valueOf(RandomUtils.nextInt(1, 100)))
            .setOfferState(OfferState.CANCELLED);
        var statusUpdate = StatusUpdate.newBuilder()
            .setTraceId(executor.getTraceId())
            .setOfferUpdate(OfferUpdate.newBuilder().setStatusUpdate(update))
            .build();
        offerServer.getStatusStream().sendStatus(titleId, statusUpdate);

        ConcurrentFinisher.wait(executor.getTraceId());

        assertEquals(OAUTH_ID, executor.getOauthId());
        assertEquals(OFFER_ID, executor.getOfferId());
        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(OfferState.CANCELLED, executor.getOfferState());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        offerServer.verifyCalls("StatusStream", 1);
        offerServer.verifyCalls("StatusConfirmation", 1);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    public void confirmOffer() throws Exception {
        final var QUOTE_ID = RandomStringUtils.randomAlphanumeric(30);
        final var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.confirmOffer(any())).thenReturn(expectedResponse);

        final var traceId = offerClient.confirmOffer(OAUTH_ID, QUOTE_ID);

        assertEquals(expectedResponse.getTraceId(), traceId);
        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertNotEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        // a short wait is needed so the status stream can be hooked up
        // before the emitting the event from the sendStatus method
        Thread.sleep(500);
        ConcurrentFinisher.start(executor.getTraceId());

        final var update = OfferStatusUpdate.newBuilder()
            .setOauthId(OAUTH_ID)
            .setQuoteId(QUOTE_ID)
            .setOfferId(QUOTE_ID)
            .setTotal(String.valueOf(RandomUtils.nextInt(1, 100)))
            .setOfferState(OfferState.CREATED);
        var statusUpdate = StatusUpdate.newBuilder()
                .setTraceId(executor.getTraceId())
                .setOfferUpdate(OfferUpdate.newBuilder().setStatusUpdate(update))
                .build();
        offerServer.getStatusStream().sendStatus(titleId, statusUpdate);

        ConcurrentFinisher.wait(executor.getTraceId());

        assertEquals(OAUTH_ID, executor.getOauthId());
        assertEquals(QUOTE_ID, executor.getOfferId());
        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(OfferState.CREATED, executor.getOfferState());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        offerServer.verifyCalls("StatusStream", 1);
        offerServer.verifyCalls("StatusConfirmation", 1);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    public void cancelListing() throws Exception {
        final var OFFER_ID = RandomStringUtils.randomAlphanumeric(30);
        final var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.cancelOffer(any())).thenReturn(expectedResponse);

        final var traceId = offerClient.cancelOffer(OAUTH_ID, OFFER_ID);

        assertEquals(expectedResponse.getTraceId(), traceId);
        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertNotEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        // a short wait is needed so the status stream can be hooked up
        // before the emitting the event from the sendStatus method
        Thread.sleep(500);
        ConcurrentFinisher.start(executor.getTraceId());

        final var update = OfferStatusUpdate.newBuilder()
            .setOauthId(OAUTH_ID)
            .setQuoteId(OFFER_ID)
            .setOfferId(OFFER_ID)
            .setTotal(String.valueOf(RandomUtils.nextInt(1, 100)))
            .setOfferState(OfferState.CANCELLED);
        var statusUpdate = StatusUpdate.newBuilder()
                .setTraceId(executor.getTraceId())
                .setOfferUpdate(OfferUpdate.newBuilder().setStatusUpdate(update))
                .build();
        offerServer.getStatusStream().sendStatus(titleId, statusUpdate);

        ConcurrentFinisher.wait(executor.getTraceId());

        assertEquals(OAUTH_ID, executor.getOauthId());
        assertEquals(OFFER_ID, executor.getOfferId());
        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(OfferState.CANCELLED, executor.getOfferState());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        offerServer.verifyCalls("StatusStream", 1);
        offerServer.verifyCalls("StatusConfirmation", 1);
    }

    @Test
    public void getOffers() throws Exception {
        var expectedResponse = OffersProto.newBuilder()
                .addOffers(OfferProto.newBuilder()
                        .setOauthId(OAUTH_ID)
                        .setCurrency("USD")
                        .setTotal("100")
                        .setGameInventoryId("game1")
                        .setCreatedTimestamp(0)
                        .build())
                .build();
        when(mockServiceBlockingStub.getOffers(any())).thenReturn(expectedResponse);
        var offersResponse = offerClient.getOffers("item123", "token123", OAUTH_ID, null);
        assertEquals(1, offersResponse.size());

        var offer = offersResponse.iterator().next();
        var expectedOffer = expectedResponse.getOffers(0);
        assertEquals(expectedOffer.getOauthId(), offer.getOauthId());
        assertEquals(expectedOffer.getCurrency(), offer.getCurrency());
        assertEquals(expectedOffer.getTotal(), offer.getTotal());
        assertEquals(expectedOffer.getGameInventoryId(), offer.getGameInventoryId());
    }
}
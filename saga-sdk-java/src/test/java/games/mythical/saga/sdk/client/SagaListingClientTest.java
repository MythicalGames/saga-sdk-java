package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.MockListingExecutor;
import games.mythical.saga.sdk.proto.api.listing.ListingProto;
import games.mythical.saga.sdk.proto.api.listing.ListingQuoteProto;
import games.mythical.saga.sdk.proto.api.listing.ListingServiceGrpc;
import games.mythical.saga.sdk.proto.api.listing.ListingsProto;
import games.mythical.saga.sdk.proto.common.ReceivedResponse;
import games.mythical.saga.sdk.proto.common.listing.ListingState;
import games.mythical.saga.sdk.proto.streams.StatusUpdate;
import games.mythical.saga.sdk.proto.streams.listing.ListingStatusUpdate;
import games.mythical.saga.sdk.server.MockServer;
import games.mythical.saga.sdk.server.stream.MockStatusStreamingImpl;
import games.mythical.saga.sdk.util.ConcurrentFinisher;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SagaListingClientTest extends AbstractClientTest {
    private static final String OAUTH_ID = UUID.randomUUID().toString();
    private static final String CURRENCY = "USD";

    private final MockListingExecutor executor = MockListingExecutor.builder().build();
    private MockServer listingServer;
    private SagaListingClient listingClient;

    @Mock
    private ListingServiceGrpc.ListingServiceBlockingStub mockServiceBlockingStub;

    @BeforeEach
    void setUp() throws Exception {
        listingServer = new MockServer(new MockStatusStreamingImpl());
        listingServer.start();
        port = listingServer.getPort();

        listingClient = setUpFactory().createSagaListingClient(executor);
        // mocking the service blocking stub clients are connected to
        FieldUtils.writeField(listingClient, "serviceBlockingStub", mockServiceBlockingStub, true);
    }

    @AfterEach
    void tearDown() throws Exception {
        listingClient.stop();
        // client shutdown is not immediate
        Thread.sleep(500);

        listingServer.stop();
    }

    @Test
    public void createListingQuote() throws Exception {
        var expectedResponse = ListingQuoteProto.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .setOauthId(OAUTH_ID)
                .setQuoteId(RandomStringUtils.randomAlphanumeric(30))
                .setGameInventoryId(RandomStringUtils.randomAlphanumeric(30))
                .setTax(String.valueOf(RandomUtils.nextInt(1, 100)))
                .setTaxCurrency(CURRENCY)
                .setTotal(String.valueOf(RandomUtils.nextInt(1, 100)))
                .setCurrency(CURRENCY)
                .setCreatedTimestamp(Instant.now().toEpochMilli() - 86400)
                .build();
        when(mockServiceBlockingStub.createListingQuote(any())).thenReturn(expectedResponse);

        var quoteResponse = listingClient.createListingQuote(
                OAUTH_ID,
                RandomStringUtils.randomAlphanumeric(30),
                BigDecimal.TEN,
                CURRENCY
        );

        assertTrue(quoteResponse.isPresent());
        var quote = quoteResponse.get();
        assertEquals(expectedResponse.getTraceId(), quote.getTraceId());
        assertEquals(OAUTH_ID, quote.getOauthId());
        assertTrue(StringUtils.isNotBlank(expectedResponse.getQuoteId()));
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    public void confirmListing() throws Exception {
        final var QUOTE_ID = RandomStringUtils.randomAlphanumeric(30);
        final var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.confirmListing(any())).thenReturn(expectedResponse);

        listingClient.confirmListing(OAUTH_ID, QUOTE_ID);

        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertNotEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        // a short wait is needed so the status stream can be hooked up
        // before the emitting the event from the sendStatus method
        Thread.sleep(500);
        ConcurrentFinisher.start(executor.getTraceId());

        var statusUpdate = StatusUpdate.newBuilder()
                .setTraceId(executor.getTraceId())
                .setListingStatus(ListingStatusUpdate.newBuilder()
                        .setOauthId(OAUTH_ID)
                        .setQuoteId(QUOTE_ID)
                        .setListingId(QUOTE_ID)
                        .setTotal(String.valueOf(RandomUtils.nextInt(1, 100)))
                        .setListingState(ListingState.CREATED))
                .build();
        listingServer.getStatusStream().sendStatus(titleId, statusUpdate);

        ConcurrentFinisher.wait(executor.getTraceId());

        assertEquals(OAUTH_ID, executor.getOauthId());
        assertEquals(QUOTE_ID, executor.getListingId());
        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(ListingState.CREATED, executor.getListingState());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        listingServer.verifyCalls("StatusStream", 1);
        listingServer.verifyCalls("StatusConfirmation", 1);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    public void cancelListing() throws Exception {
        final var LISTING_ID = RandomStringUtils.randomAlphanumeric(30);
        final var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.cancelListing(any())).thenReturn(expectedResponse);

        listingClient.cancelListing(OAUTH_ID, LISTING_ID);

        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertNotEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        // a short wait is needed so the status stream can be hooked up
        // before the emitting the event from the sendStatus method
        Thread.sleep(500);
        ConcurrentFinisher.start(executor.getTraceId());

        var statusUpdate = StatusUpdate.newBuilder()
                .setTraceId(executor.getTraceId())
                .setListingStatus(ListingStatusUpdate.newBuilder()
                        .setOauthId(OAUTH_ID)
                        .setQuoteId(LISTING_ID)
                        .setListingId(LISTING_ID)
                        .setTotal(String.valueOf(RandomUtils.nextInt(1, 100)))
                        .setListingState(ListingState.CANCELLED))
                .build();
        listingServer.getStatusStream().sendStatus(titleId, statusUpdate);

        ConcurrentFinisher.wait(executor.getTraceId());

        assertEquals(OAUTH_ID, executor.getOauthId());
        assertEquals(LISTING_ID, executor.getListingId());
        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(ListingState.CANCELLED, executor.getListingState());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        listingServer.verifyCalls("StatusStream", 1);
        listingServer.verifyCalls("StatusConfirmation", 1);
    }

    @Test
    public void getListings() throws Exception {
        var expectedResponse = ListingsProto.newBuilder()
                .addListings(ListingProto.newBuilder()
                        .setOauthId(OAUTH_ID)
                        .setCurrency("USD")
                        .setTotal("100")
                        .setGameInventoryId("game1")
                        .setCreatedTimestamp(0)
                        .build())
                .build();
        when(mockServiceBlockingStub.getListings(any())).thenReturn(expectedResponse);
        var listingsResponse = listingClient.getListings("item123", "token123", OAUTH_ID, null);
        assertEquals(1, listingsResponse.size());

        var listing = listingsResponse.iterator().next();
        var expectedListing = expectedResponse.getListings(0);
        assertEquals(expectedListing.getOauthId(), listing.getOauthId());
        assertEquals(expectedListing.getCurrency(), listing.getCurrency());
        assertEquals(expectedListing.getTotal(), listing.getTotal());
        assertEquals(expectedListing.getGameInventoryId(), listing.getGameInventoryId());
    }
}
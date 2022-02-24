package games.mythical.saga.sdk.client.model;

import games.mythical.saga.sdk.exception.IVIException;
import games.mythical.ivi.sdk.proto.api.order.FinalizeOrderAsyncResponse;
import games.mythical.ivi.sdk.proto.common.order.OrderState;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Builder
public class IVIFinalizeOrderResponse {
    private final boolean success;
    private final OrderState orderStatus;
    private final String paymentInstrumentType;
    private final String transactionId;
    private final IVIFraudScore fraudScore;
    private final String processorResponse;

    @Getter
    @Builder
    private static class IVIFraudScore {
        private final int fraudScore;
        private final String omniScore;
    }

    public static IVIFinalizeOrderResponse fromProto(FinalizeOrderAsyncResponse response) throws IVIException {

        IVIFraudScore fraudScore = null;
        if (response.hasFraudScore()) {
            fraudScore = IVIFraudScore.builder()
                    .fraudScore(response.getFraudScore().getFraudScore())
                    .omniScore(response.getFraudScore().getFraudOmniscore())
                    .build();
        }

        return IVIFinalizeOrderResponse.builder()
                .success(response.getSuccess())
                .orderStatus(response.getOrderStatus())
                .paymentInstrumentType(response.getPaymentInstrumentType())
                .transactionId(response.getTransactionId())
                .fraudScore(fraudScore)
                .processorResponse(response.getProcessorResponse())
                .build();
    }
}

package games.mythical.saga.sdk.client.model;

import games.mythical.saga.sdk.exception.IVIException;
import games.mythical.ivi.sdk.proto.api.payment.Token;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Builder
public class IVIToken {
    private final String braintreeToken;

    public static IVIToken fromProto(Token response) throws IVIException {
        var builder = IVIToken.builder();

        if (response.hasBraintree()) {
            builder.braintreeToken(response.getBraintree().getToken());
        }

        return builder.build();
    }
}

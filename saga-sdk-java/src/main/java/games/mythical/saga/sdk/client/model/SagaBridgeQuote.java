package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.saga.sdk.proto.api.bridge.QuoteBridgeNFTResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaBridgeQuote {
    private String feeInOriginchainNativeToken;
    private String feeInOrginchainNativeTokenUnit;
    private String feeInUsd;
    private String expiresAt;
    private String gasPriceOriginchain;
    private String gasPriceOriginchainUnit;
    private String gasPriceTargetchain;
    private String gasPriceTargetchainUnit;
    private String signature;

    public static SagaBridgeQuote fromProto(QuoteBridgeNFTResponse proto) {
        return ProtoUtil.toDto(proto, SagaBridgeQuote.class);
    }
}

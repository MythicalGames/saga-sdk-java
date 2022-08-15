package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.saga.sdk.proto.api.bridge.QuoteBridgeNFTRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaBridgeQuoteRequest {
    private Integer originChainId;
    private Integer targetChainId;
    private String itemTypeId;
    private String originChainWalletAddress;

    public static SagaBridgeQuoteRequest fromProto(QuoteBridgeNFTRequest proto) {
        return ProtoUtil.toDto(proto, SagaBridgeQuoteRequest.class);
    }
}
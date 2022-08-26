package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.saga.sdk.proto.api.nftbridge.NftBridgeProto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaNFTBridge {
    private String traceId;
    private String mythicalAddress;
    private String mainnetAddress;
    private String chainName;

    public static SagaNFTBridge fromProto(NftBridgeProto proto) {
        return ProtoUtil.toDto(proto, SagaNFTBridge.class);
    }
}

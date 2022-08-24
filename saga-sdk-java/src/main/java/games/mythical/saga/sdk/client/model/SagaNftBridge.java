package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.saga.sdk.proto.api.bridge.BridgeProto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaNftBridge {
    private String traceId;
    private String mythicalAddress;
    private String mainnetAddress;
    private String chainName;

    public static SagaNftBridge fromProto(BridgeProto proto) {
        return ProtoUtil.toDto(proto, SagaNftBridge.class);
    }
}

package games.mythical.saga.sdk.client.model;

import games.mythical.saga.sdk.proto.api.bridge.BridgeProto;
import games.mythical.shared.util.ProtoUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaBridge {
    private String traceId;
    private String mythicalAddress;
    private String mainnetAddress;
    private String chainName;

    public static SagaBridge fromProto(BridgeProto proto) {
        return ProtoUtil.toDto(proto, SagaBridge.class);
    }
}

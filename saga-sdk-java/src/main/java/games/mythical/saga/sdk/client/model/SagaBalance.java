package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.saga.sdk.proto.api.currencytype.PublisherBalanceProto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaBalance {
    private String address;
    private String balanceInWei;

    public static SagaBalance fromProto(PublisherBalanceProto proto) {
        var balance = ProtoUtil.toDto(proto, SagaBalance.class);
        return balance;
    }
}

package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.proto_util.dto.DtoExclude;
import games.mythical.saga.sdk.proto.api.transaction.TransactionProto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaTransaction {
    private String transactionId;
    private String titleId;
    @DtoExclude
    private Instant createdTimestamp;

    public static SagaTransaction fromProto(TransactionProto proto) {
        var transaction = ProtoUtil.toDto(proto, SagaTransaction.class);

        var createdTimestamp = Instant.ofEpochMilli(proto.getCreatedAt());
        transaction.setCreatedTimestamp(createdTimestamp);

        return transaction;
    }
}

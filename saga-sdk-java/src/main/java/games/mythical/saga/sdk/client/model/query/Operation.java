package games.mythical.saga.sdk.client.model.query;

import games.mythical.saga.sdk.proto.common.FilterOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operation extends FilterValue {
    private FilterOperation operation;

    @Override
    public String toString() {
        return operation.toString();
    }
}

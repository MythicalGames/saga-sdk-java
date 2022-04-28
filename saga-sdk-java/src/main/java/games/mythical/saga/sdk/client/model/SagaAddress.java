package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.saga.sdk.proto.api.payment.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaAddress {
    private String firstName;
    private String lastName;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String countryName;
    private String countryIsoAlpha2;

    public static SagaAddress fromProto(Address proto) {
        var address = ProtoUtil.toDto(proto, SagaAddress.class);
        return address;
    }

    public static Address toProto(SagaAddress address) {
        var proto = ProtoUtil.toProto(address, Address.class);
        return proto;
    }
}

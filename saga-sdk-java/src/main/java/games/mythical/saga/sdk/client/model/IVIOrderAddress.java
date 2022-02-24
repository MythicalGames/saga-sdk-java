package games.mythical.saga.sdk.client.model;

import games.mythical.ivi.sdk.proto.api.order.Address;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IVIOrderAddress {
    private String firstName;
    private String lastName;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String countryName;
    private String countryIsoAlpha2;

    public Address toProto() {
        return Address.newBuilder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setAddressLine1(addressLine1)
                .setAddressLine2(addressLine2)
                .setCity(city)
                .setState(state)
                .setPostalCode(postalCode)
                .setCountryName(countryName)
                .setCountryIsoAlpha2(countryIsoAlpha2)
                .build();
    }

    public static IVIOrderAddress fromProto(Address address) {
        return IVIOrderAddress.builder()
                .firstName(address.getFirstName())
                .lastName(address.getLastName())
                .addressLine1(address.getAddressLine1())
                .addressLine2(address.getAddressLine2())
                .city(address.getCity())
                .state(address.getState())
                .postalCode(address.getPostalCode())
                .countryName(address.getCountryName())
                .countryIsoAlpha2(address.getCountryIsoAlpha2())
                .build();
    }
}

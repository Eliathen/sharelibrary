package pl.szymanski.sharelibrary.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.szymanski.sharelibrary.entity.Address;

@AllArgsConstructor
@Data
public class AddressResponse {
    private Long id;
    private String country;
    private String city;
    private String postalCode;
    private String street;
    private String buildingNumber;

    private CoordinatesResponse coordinates;

    public static AddressResponse of(Address address) {
        return new AddressResponse(
                address.getId(),
                address.getCountry(),
                address.getCity(),
                address.getPostalCode(),
                address.getStreet(),
                address.getBuildingNumber(),
                CoordinatesResponse.of(address.getCoordinates())
        );
    }
}

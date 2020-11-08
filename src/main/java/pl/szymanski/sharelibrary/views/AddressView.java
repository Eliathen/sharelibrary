package pl.szymanski.sharelibrary.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.szymanski.sharelibrary.entity.Address;

@AllArgsConstructor
@Data
public class AddressView {
    private Long id;
    private String country;
    private String city;
    private String street;
    private String buildingNumber;

    private CoordinatesView coordinates;

    public static AddressView of(Address address) {
        return new AddressView(
                address.getId(),
                address.getCountry(),
                address.getCity(),
                address.getStreet(),
                address.getBuildingNumber(),
                CoordinatesView.of(address.getCoordinates())
        );
    }
}

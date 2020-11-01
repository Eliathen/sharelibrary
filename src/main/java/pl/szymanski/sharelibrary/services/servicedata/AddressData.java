package pl.szymanski.sharelibrary.services.servicedata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import pl.szymanski.sharelibrary.models.Address;

@Builder
@AllArgsConstructor
public class AddressData {

    private Long id;
    private String city;
    private String street;
    private String buildingNumber;
    private CoordinatesData coordinates;

    public static AddressData of(Address address) {
        return AddressData
                .builder()
                .id(address.getId())
                .city(address.getCity())
                .street(address.getStreet())
                .buildingNumber(address.getBuildingNumber())
                .coordinates(CoordinatesData.of(address.getCoordinates()))
                .build();
    }

}

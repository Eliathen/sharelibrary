package pl.szymanski.sharelibrary.services.ports;

import pl.szymanski.sharelibrary.entity.Address;
import pl.szymanski.sharelibrary.entity.Coordinates;

public interface GeoCoordinateApiService {

    Coordinates getCoordinatesByAddress(Address address);

    Address getAddressByCoordinates(Coordinates coordinates);
}

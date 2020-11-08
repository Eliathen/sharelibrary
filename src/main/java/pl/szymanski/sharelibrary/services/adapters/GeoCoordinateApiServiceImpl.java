package pl.szymanski.sharelibrary.services.adapters;

import org.springframework.stereotype.Service;
import pl.szymanski.sharelibrary.entity.Address;
import pl.szymanski.sharelibrary.entity.Coordinates;
import pl.szymanski.sharelibrary.services.ports.GeoCoordinateApiService;

@Service
public class GeoCoordinateApiServiceImpl implements GeoCoordinateApiService {

    @Override
    public Coordinates getCoordinatesByAddress(Address address) {
        return null;
    }

    @Override
    public Address getAddressByCoordinates(Coordinates coordinates) {
        return null;
    }
}

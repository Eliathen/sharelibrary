package pl.szymanski.sharelibrary.repositories.ports;

import pl.szymanski.sharelibrary.entity.Coordinates;

import java.util.Optional;

public interface CoordinatesRepository {

    Coordinates saveCoordinates(Coordinates coordinates);

    Optional<Coordinates> findAllByLatitudeAndLongitude(Double latitude, Double longitude);

}

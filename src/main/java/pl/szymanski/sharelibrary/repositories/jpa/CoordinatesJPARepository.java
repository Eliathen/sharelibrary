package pl.szymanski.sharelibrary.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szymanski.sharelibrary.entity.Coordinates;

import java.util.Optional;

public interface CoordinatesJPARepository extends JpaRepository<Coordinates, Long> {

    Optional<Coordinates> findAllByLatitudeAndLongitude(Double latitude, Double longitude);
}

package pl.szymanski.sharelibrary.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.szymanski.sharelibrary.entity.Exchange;
import pl.szymanski.sharelibrary.enums.ExchangeStatus;

import java.util.List;

public interface ExchangeJPARepository extends JpaRepository<Exchange, Long> {

    List<Exchange> findAllByExchangeStatus(ExchangeStatus exchangeStatus);

    @Query(
            value = "SELECT * FROM Exchange e JOIN Coordinates c ON e.COORDINATES_ID = c.ID WHERE ( " +
                    "    acos(" +
                    "        sin(c.latitude * 0.0175) * sin(:latitude * 0.0175) + " +
                    "        cos(c.latitude * 0.0175) * cos(:latitude * 0.0175) * " +
                    "        cos ((:longitude * 0.0175) - (c.longitude * 0.0175)) " +
                    "    ) * 6371 <= :radius" +
                    ")", nativeQuery = true
    )
    List<Exchange> findByLatitudeAndLongitudeAndRadius(@Param("latitude") Double latitude,
                                                       @Param("longitude") Double longitude,
                                                       @Param("radius") Double radius);
}

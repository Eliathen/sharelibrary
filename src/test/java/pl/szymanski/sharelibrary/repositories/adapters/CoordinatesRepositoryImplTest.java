package pl.szymanski.sharelibrary.repositories.adapters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.szymanski.sharelibrary.entity.Coordinates;
import pl.szymanski.sharelibrary.repositories.jpa.CoordinatesJPARepository;
import pl.szymanski.sharelibrary.repositories.ports.CoordinatesRepository;
import pl.szymanski.sharelibrary.utils.generator.CoordinatesGenerator;

import java.util.Optional;


@SpringBootTest
class CoordinatesRepositoryImplTest {

    @Autowired
    private CoordinatesJPARepository coordinatesJPARepository;

    @Autowired
    private CoordinatesRepository coordinatesRepository;

    @BeforeEach
    void setUp() {
        coordinatesJPARepository.deleteAll();
    }

    @AfterEach
    void cleanUp() {
        coordinatesJPARepository.deleteAll();
    }

    @Test
    void should_add_coordinates() {
        //given
        Coordinates in = CoordinatesGenerator.getCoordinates();
        //when
        Coordinates out = coordinatesJPARepository.saveAndFlush(in);
        //then
        Assertions.assertThat(out).isEqualTo(in);
    }

    @Test
    void should_return_coordinates() {
        //given
        Coordinates in = CoordinatesGenerator.getCoordinates();
        coordinatesJPARepository.saveAndFlush(in);
        //when
        Optional<Coordinates> out = coordinatesRepository.findByLatitudeAndLongitude(in.getLatitude(), in.getLongitude());
        //given
        Assertions.assertThat(out).isPresent().containsInstanceOf(Coordinates.class);
    }

}
package pl.szymanski.sharelibrary.repositories.adapters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.szymanski.sharelibrary.entity.Coordinates;
import pl.szymanski.sharelibrary.repositories.jpa.CoordinatesJPARepository;
import pl.szymanski.sharelibrary.repositories.ports.CoordinatesRepository;
import pl.szymanski.sharelibrary.utils.generator.CoordinatesGenerator;

import java.util.Optional;

import static org.mockito.Mockito.times;


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
    void shouldAddCoordinates() {
        //given
        Coordinates in = CoordinatesGenerator.getCoordinates();
        //when
        Coordinates out = coordinatesRepository.saveCoordinates(in);
        //then
        Assertions.assertThat(out.getLatitude()).isEqualTo(in.getLatitude());
        Assertions.assertThat(out.getLongitude()).isEqualTo(in.getLongitude());
    }

    @Test
    void shouldReturnCoordinates() {
        //given
        Coordinates in = CoordinatesGenerator.getCoordinates();
        coordinatesJPARepository.saveAndFlush(in);
        //when
        Optional<Coordinates> out = coordinatesRepository.findByLatitudeAndLongitude(in.getLatitude(), in.getLongitude());
        //given
        Assertions.assertThat(out).isPresent().containsInstanceOf(Coordinates.class);
    }

    @Test
    void shouldCallMethodFindByLatitudeAndLongitudeFromJpaRepository() {
        //given
        Coordinates coordinates = CoordinatesGenerator.getCoordinates();
        CoordinatesJPARepository coordinatesJPARepository = Mockito.mock(CoordinatesJPARepository.class);
        CoordinatesRepository repository = new CoordinatesRepositoryImpl(coordinatesJPARepository);
        //when
        repository.findByLatitudeAndLongitude(coordinates.getLatitude(), coordinates.getLongitude());
        //then
        Mockito.verify(coordinatesJPARepository, times(1))
                .findByLatitudeAndLongitude(coordinates.getLatitude(), coordinates.getLongitude());
    }

    @Test
    void shouldCallMethodSaveAndFlushFromJpaRepository() {
        //given
        Coordinates coordinates = CoordinatesGenerator.getCoordinates();
        CoordinatesJPARepository coordinatesJPARepository = Mockito.mock(CoordinatesJPARepository.class);
        CoordinatesRepository repository = new CoordinatesRepositoryImpl(coordinatesJPARepository);
        //when
        repository.saveCoordinates(coordinates);
        //then
        Mockito.verify(coordinatesJPARepository, times(1))
                .saveAndFlush(coordinates);
    }

}
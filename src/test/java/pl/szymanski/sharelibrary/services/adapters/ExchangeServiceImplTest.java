package pl.szymanski.sharelibrary.services.adapters;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExchangeServiceImplTest {

    @InjectMocks
    private ExchangeServiceImpl sut;

    @Test
    void shouldReturnValueNearZero() {
        //given
        double lat1 = 51.5;
        double lon1 = 0;
        double lat2 = 51.5;
        double lon2 = 0;
        //when
        double distance = sut.countDistanceBetweenPoints(lat1, lon1, lat2, lon2);
        //then
        Assertions.assertThat(distance).isCloseTo(0, Percentage.withPercentage(0.001));
    }

    @Test
    void shouldReturnCorrectValue() {
        //given
        double lat1 = 51.5;
        double lon1 = 0;
        double lat2 = 38.8;
        double lon2 = -77.1;
        //when
        double distance = sut.countDistanceBetweenPoints(lat1, lon1, lat2, lon2);
        //then
        Assertions.assertThat(distance).isCloseTo(5918185.064088764, Percentage.withPercentage(0.001));
    }

    @Test
    void shouldReturnValueNear200() {
        //given
        double lat1 = 52.1968542;
        double lon1 = 20.9230059;
        double lat2 = 52.1959335;
        double lon2 = 20.9257954;
        //when
        double distance = sut.countDistanceBetweenPoints(lat1, lon1, lat2, lon2);
        //then
        Assertions.assertThat(distance).isCloseTo(200, Percentage.withPercentage(10));
    }
}
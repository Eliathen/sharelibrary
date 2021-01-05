package pl.szymanski.sharelibrary.utils.generator;

import pl.szymanski.sharelibrary.entity.Coordinates;
import pl.szymanski.sharelibrary.entity.Exchange;
import pl.szymanski.sharelibrary.utils.constant.CoordinatesConstant;

import java.util.ArrayList;

public class CoordinatesGenerator {

    public static Coordinates getCoordinates() {
        Coordinates coordinates = new Coordinates();
        coordinates.setLatitude(CoordinatesConstant.TEST_LATITUDE);
        coordinates.setLongitude(CoordinatesConstant.TEST_LONGITUDE);
        coordinates.setExchanges(new ArrayList<Exchange>());
        return coordinates;
    }

}

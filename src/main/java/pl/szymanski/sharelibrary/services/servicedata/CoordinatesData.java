package pl.szymanski.sharelibrary.services.servicedata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import pl.szymanski.sharelibrary.models.Coordinates;

@Builder
@AllArgsConstructor
public class CoordinatesData {

    private Long id;
    private Double latitude;
    private Double longitude;

    public static CoordinatesData of(Coordinates coordinates) {
        return CoordinatesData
                .builder()
                .id(coordinates.getId())
                .latitude(coordinates.getLatitude())
                .longitude(coordinates.getLongitude())
                .build();
    }
}

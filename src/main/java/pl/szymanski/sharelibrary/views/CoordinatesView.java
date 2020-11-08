package pl.szymanski.sharelibrary.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.szymanski.sharelibrary.entity.Coordinates;

@AllArgsConstructor
@Data
public class CoordinatesView {

    private Long id;
    private Double latitude;
    private Double longitude;

    public static CoordinatesView of(Coordinates coordinates) {
        return new CoordinatesView(
                coordinates.getId(),
                coordinates.getLatitude(),
                coordinates.getLongitude()
        );
    }
}

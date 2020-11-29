package pl.szymanski.sharelibrary.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.szymanski.sharelibrary.entity.User;

@AllArgsConstructor
@Data
public class BaseUserResponse {

    private Long id;
    private String email;
    private String username;
    private String name;
    private String surname;
    private CoordinatesResponse coordinates;


    public static BaseUserResponse of(User user) {
        return new BaseUserResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getName(),
                user.getSurname(),
                CoordinatesResponse.of(user.getCoordinates())
        );
    }
}

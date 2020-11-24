package pl.szymanski.sharelibrary.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.szymanski.sharelibrary.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
public class UserResponse {

    private Long id;
    private String email;
    private String username;
    private String name;
    private String surname;

    private CoordinatesResponse coordinatesResponse;

    private List<BookWithoutUsersResponse> books;

    public static UserResponse of(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getName(),
                user.getSurname(),
                CoordinatesResponse.of(user.getCoordinates()),
                user.getBooks().stream().map(BookWithoutUsersResponse::of).collect(Collectors.toList())
        );
    }
}

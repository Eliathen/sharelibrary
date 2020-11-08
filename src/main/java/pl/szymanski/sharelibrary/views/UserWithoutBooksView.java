package pl.szymanski.sharelibrary.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.szymanski.sharelibrary.entity.User;

@AllArgsConstructor
@Data
public class UserWithoutBooksView {

    private Long id;
    private String email;
    private String username;
    private String name;
    private String surname;

    private AddressView addressView;

    public static UserWithoutBooksView of(User user) {
        return new UserWithoutBooksView(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getName(),
                user.getSurname(),
                AddressView.of(user.getDefaultAddress())
        );
    }
}

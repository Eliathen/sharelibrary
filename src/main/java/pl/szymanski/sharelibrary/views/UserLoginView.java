package pl.szymanski.sharelibrary.views;

import lombok.Value;
import pl.szymanski.sharelibrary.entity.User;
import pl.szymanski.sharelibrary.security.JwtAuthenticationResponse;

@Value
public class UserLoginView {
    private Long id;
    private String userName;
    private JwtAuthenticationResponse response;

    public static UserLoginView of(User user, JwtAuthenticationResponse jwtAuthenticationResponse) {
        return new UserLoginView(
                user.getId(),
                user.getUsername(),
                jwtAuthenticationResponse
        );
    }
}

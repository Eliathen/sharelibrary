package pl.szymanski.sharelibrary.views;

import lombok.Value;
import pl.szymanski.sharelibrary.entity.User;
import pl.szymanski.sharelibrary.security.JwtAuthenticationResponse;

@Value
public class UserLoginResponse {
    private Long id;
    private String userName;
    private JwtAuthenticationResponse response;

    public static UserLoginResponse of(User user, JwtAuthenticationResponse jwtAuthenticationResponse) {
        return new UserLoginResponse(
                user.getId(),
                user.getUsername(),
                jwtAuthenticationResponse
        );
    }
}

package pl.szymanski.sharelibrary.services.ports;

import pl.szymanski.sharelibrary.commanddata.LoginCommandData;
import pl.szymanski.sharelibrary.entity.User;
import pl.szymanski.sharelibrary.security.JwtAuthenticationResponse;

import java.util.Optional;
import java.util.Set;

public interface UserService {

    Optional<User> getUserByEmail(String email);

    Set<User> getUsers();

    User saveUser(User user);

    User getUserByEmailOrUserName(String user);

    JwtAuthenticationResponse getJwt(LoginCommandData loginCommandData);

    User assignBookToUser(Long userId, Long bookId);
}

package pl.szymanski.sharelibrary.services.ports;

import pl.szymanski.sharelibrary.commanddata.LoginRequest;
import pl.szymanski.sharelibrary.entity.User;
import pl.szymanski.sharelibrary.security.JwtAuthenticationResponse;

import java.util.Optional;
import java.util.Set;

public interface UserService {

    Optional<User> getUserByEmail(String email);

    Set<User> getUsers();

    User saveUser(User user);

    User getUserByEmailOrUserName(String user);

    JwtAuthenticationResponse getJwt(LoginRequest loginRequest);

    User assignBookToUser(Long userId, Long bookId);

    User changeUserDetails(Long id, User user);

    User getUserById(Long id);

    User withdrawBookFromUser(Long userId, Long bookId);
}

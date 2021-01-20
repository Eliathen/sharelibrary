package pl.szymanski.sharelibrary.services.ports;

import pl.szymanski.sharelibrary.entity.User;
import pl.szymanski.sharelibrary.requests.EditUserRequest;
import pl.szymanski.sharelibrary.requests.LoginRequest;
import pl.szymanski.sharelibrary.security.JwtAuthenticationResponse;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {

    Optional<User> getUserByEmail(String email);

    Set<User> getUsers();

    User saveUser(User user);

    User getUserByEmailOrUserName(String user);

    JwtAuthenticationResponse getJwt(LoginRequest loginRequest);

    User assignBookToUser(Long userId, Long bookId);

    User changeUserDetails(Long id, EditUserRequest editUserRequest);

    User getUserById(Long id);

    User withdrawBookFromUser(Long userId, Long bookId);

    List<User> getUsersWithBooksWhereAtUserIs(Long userId);
}

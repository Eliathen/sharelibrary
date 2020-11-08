package pl.szymanski.sharelibrary.repositories.ports;

import pl.szymanski.sharelibrary.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User saveUser(User user);

    Optional<User> getUserById(Long id);

    Optional<User> getUserByEmail(String email);

    List<User> getUsers();

    Optional<User> getUserByUsernameOrEmail(String user, String email);


}

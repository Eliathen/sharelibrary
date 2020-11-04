package pl.szymanski.sharelibrary.services.ports;

import pl.szymanski.sharelibrary.entity.User;

import java.util.Optional;
import java.util.Set;

public interface UserService {

    Optional<User> getUserByEmail(String email);

    Set<User> getUsers();

    User saveUser(User user);


}

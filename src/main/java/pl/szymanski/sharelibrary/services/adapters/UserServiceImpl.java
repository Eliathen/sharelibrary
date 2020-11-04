package pl.szymanski.sharelibrary.services.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szymanski.sharelibrary.entity.User;
import pl.szymanski.sharelibrary.repositories.ports.UserRepository;
import pl.szymanski.sharelibrary.services.ports.UserService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public Set<User> getUsers() {
        return new HashSet<>(userRepository.getUsers());
    }

    @Override
    public User saveUser(User user) {
        return userRepository.saveUser(user);
    }
}

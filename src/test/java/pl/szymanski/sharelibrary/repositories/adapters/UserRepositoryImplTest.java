package pl.szymanski.sharelibrary.repositories.adapters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.szymanski.sharelibrary.entity.User;
import pl.szymanski.sharelibrary.repositories.jpa.UserJPARepository;
import pl.szymanski.sharelibrary.utils.generator.UserGenerator;

import java.util.Optional;

@SpringBootTest
class UserRepositoryImplTest {

    @Autowired
    UserJPARepository userJPARepository;

    @Autowired
    UserRepositoryImpl userRepository;

    @BeforeEach
    void setUp() {
        userJPARepository.deleteAll();
    }

    @AfterEach
    void cleanUp() {
        userJPARepository.deleteAll();
    }


    @Test
    void shouldSaveUser() {
        User user = UserGenerator.getUser();
        User newUser = userRepository.saveUser(user);
        Assertions.assertThat(newUser.getEmail()).isEqualTo(user.getEmail());
        Assertions.assertThat(newUser.getUsername()).isEqualTo(user.getUsername());
        Assertions.assertThat(newUser.getName()).isEqualTo(user.getName());
        Assertions.assertThat(newUser.getSurname()).isEqualTo(user.getSurname());

    }

    @Test
    void shouldReturnUserByGivenId() {
        User user = userJPARepository.saveAndFlush(UserGenerator.getUser());
        Optional<User> newUser = userRepository.getUserById(user.getId());

        Assertions.assertThat(newUser.isPresent()).isEqualTo(true);
        Assertions.assertThat(user.getId()).isEqualTo(newUser.get().getId());

    }

    @Test
    void shouldReturnUserByGivenEmail() {
        User user = userJPARepository.saveAndFlush(UserGenerator.getUser());
        Optional<User> newUser = userRepository.getUserByEmail(user.getEmail());

        Assertions.assertThat(newUser.isPresent()).isEqualTo(true);
        Assertions.assertThat(user.getEmail()).isEqualTo(newUser.get().getEmail());

    }

    @Test
    void shouldReturnUserByGivenUsername() {
        User user = userJPARepository.saveAndFlush(UserGenerator.getUser());
        Optional<User> newUser = userRepository.getUserByUsername(user.getUsername());

        Assertions.assertThat(newUser.isPresent()).isEqualTo(true);
        Assertions.assertThat(user.getUsername()).isEqualTo(newUser.get().getUsername());

    }

    @Test
    void shouldReturnUserByGivenUsernameOrEmail() {
        User user = userJPARepository.saveAndFlush(UserGenerator.getUser());
        Optional<User> first = userRepository.getUserById(user.getId());
        Optional<User> second = userRepository.getUserById(user.getId());

        Assertions.assertThat(first.isPresent()).isEqualTo(true);
        Assertions.assertThat(second.isPresent()).isEqualTo(true);
        Assertions.assertThat(first.get().getUsername()).isEqualTo(second.get().getUsername());
        Assertions.assertThat(first.get().getEmail()).isEqualTo(second.get().getEmail());
        Assertions.assertThat(first.get().getId()).isEqualTo(second.get().getId());

    }


}
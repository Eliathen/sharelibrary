package pl.szymanski.sharelibrary.services.adapters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.szymanski.sharelibrary.converters.RequestConverter;
import pl.szymanski.sharelibrary.entity.Book;
import pl.szymanski.sharelibrary.entity.User;
import pl.szymanski.sharelibrary.entity.UserBook;
import pl.szymanski.sharelibrary.exceptions.auth.InvalidUsernameEmailOrPassword;
import pl.szymanski.sharelibrary.exceptions.users.EmailAlreadyExist;
import pl.szymanski.sharelibrary.exceptions.users.UserNotFoundById;
import pl.szymanski.sharelibrary.exceptions.users.UsernameAlreadyExists;
import pl.szymanski.sharelibrary.repositories.ports.BookRepository;
import pl.szymanski.sharelibrary.repositories.ports.CoordinatesRepository;
import pl.szymanski.sharelibrary.repositories.ports.UserRepository;
import pl.szymanski.sharelibrary.requests.EditUserRequest;
import pl.szymanski.sharelibrary.security.JwtTokenProvider;
import pl.szymanski.sharelibrary.utils.generator.BookGenerator;
import pl.szymanski.sharelibrary.utils.generator.UserGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    UserRepository userRepository;
    @Mock
    BookRepository bookRepository;
    @Mock
    CoordinatesRepository coordinatesRepository;
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    JwtTokenProvider tokenProvider;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void shouldReturnUserByEmail() {
        //given
        User user = UserGenerator.getUser();
        when(userRepository.getUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        //when
        Optional<User> result = userService.getUserByEmail(user.getEmail());
        //then
        Assertions.assertThat(result.isPresent()).isEqualTo(true);
        Assertions.assertThat(result.get().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void shouldThrowExceptionInvalidUsernameEmailOrPassword() {
        //given
        User user = UserGenerator.getUser();
        when(userRepository.getUserByUsernameOrEmail(any(), any()))
                .thenReturn(Optional.empty());
        //when & then
        Assertions.assertThatThrownBy(() ->
                userService.getUserByEmailOrUserName(user.getEmail()))
                .isInstanceOf(InvalidUsernameEmailOrPassword.class);
    }

    @Test
    void shouldThrowExceptionEmailAlreadyExists() {
        //given
        User user = UserGenerator.getUser();
        when(userRepository.getUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        //when & then
        Assertions.assertThatThrownBy(() ->
                userService.saveUser(user)
        ).isInstanceOf(EmailAlreadyExist.class);

    }

    @Test
    void shouldThrowExceptionUsernameAlreadyExists() {
        //given
        User user = UserGenerator.getUser();
        when(userRepository.getUserByUsername(user.getUsername())).thenReturn(Optional.of(user));
        //when & then
        Assertions.assertThatThrownBy(() ->
                userService.saveUser(user)
        ).isInstanceOf(UsernameAlreadyExists.class);

    }

    @Test
    void shouldThrowExceptionUserNotFoundById() {
        //given
        User user = UserGenerator.getUser();
        EditUserRequest editUserRequest = UserGenerator.getEditUserRequest();
        when(userRepository.getUserById(user.getId())).thenReturn(Optional.empty());
        //when & then
        Assertions.assertThatThrownBy(() ->
                userService.changeUserDetails(user.getId(), editUserRequest)
        ).isInstanceOf(UserNotFoundById.class);
    }

    @Test
    void shouldRemoveBookFromUser() {
        User user = UserGenerator.getUser();
        when(userRepository.getUserById(user.getId())).thenReturn(Optional.of(user));
        UserBook userBook = BookGenerator.getUserBook();
        user.setBooks(new ArrayList<>(Arrays.asList(userBook)));
        User result = userService.withdrawBookFromUser(user.getId(), userBook.getBook().getId());
        Assertions.assertThat(result.getBooks()).isNullOrEmpty();
    }

    @Test
    void shouldEditUser() {
        //given
        EditUserRequest editUserRequest = UserGenerator.getEditUserRequest();
        User user = UserGenerator.getUser();
        User newUser = UserGenerator.getUser();
        newUser.setName(editUserRequest.getName());
        newUser.setSurname(editUserRequest.getSurname());
        newUser.setCoordinates(RequestConverter.coordinatesRequestToCoordinates(editUserRequest.getCoordinates()));
        when(userRepository.getUserById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.saveUser(any())).thenReturn(newUser);
        //when
        User result = userService.changeUserDetails(user.getId(), editUserRequest);
        //then
        Assertions.assertThat(result.getName()).isEqualTo(newUser.getName());
        Assertions.assertThat(result.getSurname()).isEqualTo(newUser.getSurname());
        Assertions.assertThat(result.getCoordinates()).isEqualTo(newUser.getCoordinates());

    }

    @Test
    void shouldReturnUserById() {
        //given
        User user = UserGenerator.getUser();
        when(userRepository.getUserById(user.getId())).thenReturn(Optional.of(user));
        //when
        User result = userService.getUserById(user.getId());
        //then
        Assertions.assertThat(result.getId()).isEqualTo(user.getId());
    }

    @Test
    void shouldAssignBookToUser() {
        //given
        User user = UserGenerator.getUser();
        Book book = BookGenerator.getBook();
        when(userRepository.getUserById(user.getId())).thenReturn(Optional.of(user));
        when(bookRepository.getBookById(book.getId())).thenReturn(Optional.of(book));
        User newUser = UserGenerator.getUser();
        UserBook userBook = BookGenerator.getUserBook();
        userBook.setBook(book);
        userBook.setUser(user);
        newUser.setBooks(new ArrayList<>(Arrays.asList(userBook)));
        when(userRepository.saveUser(any())).thenReturn(newUser);
        //when
        User result = userService.assignBookToUser(user.getId(), book.getId());
        //then
        Assertions.assertThat(result.getBooks().stream().findFirst().get().getUser().getId()).isEqualTo(user.getId());
        Assertions.assertThat(result.getBooks().stream().findFirst().get().getBook().getId()).isEqualTo(book.getId());
    }
}
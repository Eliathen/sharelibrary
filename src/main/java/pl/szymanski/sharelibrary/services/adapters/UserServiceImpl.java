package pl.szymanski.sharelibrary.services.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.szymanski.sharelibrary.commanddata.LoginCommandData;
import pl.szymanski.sharelibrary.entity.Address;
import pl.szymanski.sharelibrary.entity.Book;
import pl.szymanski.sharelibrary.entity.Coordinates;
import pl.szymanski.sharelibrary.entity.User;
import pl.szymanski.sharelibrary.exceptions.auth.InvalidUsernameEmailOrPassword;
import pl.szymanski.sharelibrary.exceptions.books.BookDoesNotExist;
import pl.szymanski.sharelibrary.exceptions.users.EmailAlreadyExist;
import pl.szymanski.sharelibrary.exceptions.users.UserNotFoundById;
import pl.szymanski.sharelibrary.exceptions.users.UsernameAlreadyExists;
import pl.szymanski.sharelibrary.repositories.ports.AddressRepository;
import pl.szymanski.sharelibrary.repositories.ports.BookRepository;
import pl.szymanski.sharelibrary.repositories.ports.UserRepository;
import pl.szymanski.sharelibrary.security.JwtAuthenticationResponse;
import pl.szymanski.sharelibrary.security.JwtTokenProvider;
import pl.szymanski.sharelibrary.services.ports.GeoCoordinateApiService;
import pl.szymanski.sharelibrary.services.ports.UserService;
import pl.szymanski.sharelibrary.utilities.Utils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final AddressRepository addressRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final GeoCoordinateApiService geoCoordinateApiService;

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public Set<User> getUsers() {
        return new HashSet<>(userRepository.getUsers());
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        validateUser(user);
        user.setPassword(passwordEncoder.encode(String.valueOf(user.getPassword())).toCharArray());
        Optional<Address> address = checkIfAddressExists(user.getDefaultAddress());
        if (address.isPresent()) {
            user.setDefaultAddress(address.get());
        } else {
            Coordinates coordinates = geoCoordinateApiService.getCoordinatesByAddress(user.getDefaultAddress());
//            user.getDefaultAddress().getCoordinates().setLatitude(coordinates.getLatitude());
//            user.getDefaultAddress().getCoordinates().setLongitude(coordinates.getLongitude());
            user.getDefaultAddress().getCoordinates().setLatitude(50.12);
            user.getDefaultAddress().getCoordinates().setLongitude(50.13);
        }
        return userRepository.saveUser(user);
    }


    @Override
    public User getUserByEmailOrUserName(String user) {
        return userRepository.getUserByUsernameOrEmail(user, user).orElseThrow(InvalidUsernameEmailOrPassword::new);
    }

    @Override
    public JwtAuthenticationResponse getJwt(LoginCommandData loginCommandData) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginCommandData.getUserNameOrEmail(),
                        String.valueOf(loginCommandData.getPassword())
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return new JwtAuthenticationResponse(jwt);
    }

    @Override
    @Transactional
    public User assignBookToUser(Long userId, Long bookId) {
        User user = userRepository.getUserById(userId).orElseThrow(() -> new UserNotFoundById(userId));
        Book book = bookRepository.getBookById(bookId).orElseThrow(() -> new BookDoesNotExist(bookId));
        Set<Book> usersBooks = user.getBooks();
        usersBooks.add(book);
        user.setBooks(usersBooks);
        return userRepository.saveUser(user);
    }

    private void validateUser(User user) {
        if (userRepository.getUserByEmail(user.getEmail()).isPresent()) {
            throw new EmailAlreadyExist(user.getEmail());
        } else if (userRepository.getUserByUsername(user.getUsername()).isPresent()) {
            throw new UsernameAlreadyExists(user.getUsername());
        }
        Utils.validateEmailAddress(user.getEmail());
    }

    private Optional<Address> checkIfAddressExists(Address address) {
        return addressRepository.getAddressByCountryAndPostalCodeAndCityAndStreetAndBuildingNumber(
                address.getCountry(),
                address.getCity(),
                address.getPostalCode(),
                address.getStreet(),
                address.getBuildingNumber()
        );
    }

}

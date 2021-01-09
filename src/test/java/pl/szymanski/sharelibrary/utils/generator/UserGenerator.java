package pl.szymanski.sharelibrary.utils.generator;

import pl.szymanski.sharelibrary.entity.User;
import pl.szymanski.sharelibrary.entity.UserBook;
import pl.szymanski.sharelibrary.requests.EditUserRequest;
import pl.szymanski.sharelibrary.requests.LoginRequest;
import pl.szymanski.sharelibrary.requests.RemoveBookFromUserRequest;
import pl.szymanski.sharelibrary.requests.UserRequest;

import java.util.ArrayList;
import java.util.List;

public class UserGenerator {

    public static UserRequest getUserRequest() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("John");
        userRequest.setSurname("Dee");
        userRequest.setUsername("johnDee");
        userRequest.setEmail("john@dee.com");
        userRequest.setPassword(new char[]{'z', 'a', 'q', '1', '@', 'W', 'S', 'X'});
        userRequest.setCoordinates(CoordinatesGenerator.getCoordinatesRequest());
        return userRequest;
    }

    public static User getUser() {
        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setSurname("Dee");
        user.setUsername("johnDee");
        user.setEmail("john@dee.com");
        user.setCoordinates(CoordinatesGenerator.getCoordinates());
        return user;
    }

    public static User getUserWithBooks() {
        User user = new User();
        user.setName("John");
        user.setSurname("Dee");
        user.setUsername("johnDee");
        user.setEmail("john@dee.com");
        user.setCoordinates(CoordinatesGenerator.getCoordinates());
        List<UserBook> userBooks = new ArrayList<>();
        userBooks.add(BookGenerator.getUserBook());
        user.setBooks(userBooks);
        return user;
    }

    public static LoginRequest getLoginRequest() {
        return new LoginRequest(
                "z", new char[]{'z'}
        );
    }

    public static EditUserRequest getEditUserRequest() {
        return new EditUserRequest(
                "John",
                "Dee",
                CoordinatesGenerator.getCoordinatesRequest()
        );
    }

    public static RemoveBookFromUserRequest getRemoveBookFromUserRequest() {
        return new RemoveBookFromUserRequest(
                1L, 1L
        );
    }
}

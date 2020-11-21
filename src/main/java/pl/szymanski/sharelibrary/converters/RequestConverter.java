package pl.szymanski.sharelibrary.converters;

import org.springframework.beans.BeanUtils;
import pl.szymanski.sharelibrary.commanddata.AddBookRequest;
import pl.szymanski.sharelibrary.commanddata.AuthorRequest;
import pl.szymanski.sharelibrary.commanddata.CoordinatesRequest;
import pl.szymanski.sharelibrary.commanddata.UserRequest;
import pl.szymanski.sharelibrary.entity.Author;
import pl.szymanski.sharelibrary.entity.Book;
import pl.szymanski.sharelibrary.entity.Coordinates;
import pl.szymanski.sharelibrary.entity.User;

import java.util.LinkedList;
import java.util.List;

public class RequestConverter {

    public static Book addBookRequestToBook(AddBookRequest addBookRequest) {
        Book book = new Book();
        List<Author> authors = new LinkedList<>();
        addBookRequest.getAuthors().forEach(it -> authors.add(authorCommandRequestToAuthor(it)));
        book.setAuthors(authors);
        BeanUtils.copyProperties(addBookRequest, book, "authors", "image");
        return book;
    }

    public static Author authorCommandRequestToAuthor(AuthorRequest authorRequest) {
        Author author = new Author();
        BeanUtils.copyProperties(authorRequest, author);
        return author;
    }

    public static Coordinates coordinatesRequestToCoordinates(CoordinatesRequest coordinatesRequest) {

        Coordinates coordinates = new Coordinates();
        BeanUtils.copyProperties(coordinatesRequest, coordinates);
        return coordinates;
    }

    public static User userRequestToUser(UserRequest userRequest) {
        User user = new User();
        user.setCoordinates(coordinatesRequestToCoordinates(userRequest.getCoordinates()));
        BeanUtils.copyProperties(userRequest, user);
        return user;
    }


}

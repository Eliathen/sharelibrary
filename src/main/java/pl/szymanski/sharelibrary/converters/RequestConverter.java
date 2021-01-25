package pl.szymanski.sharelibrary.converters;

import org.springframework.beans.BeanUtils;
import pl.szymanski.sharelibrary.entity.*;
import pl.szymanski.sharelibrary.enums.BookCondition;
import pl.szymanski.sharelibrary.requests.*;

import java.util.LinkedList;
import java.util.List;

public class RequestConverter {

    public static Book addBookRequestToBook(AddBookRequest addBookRequest) {
        Book book = new Book();
        List<Author> authors = new LinkedList<>();
        addBookRequest.getAuthors().forEach(it -> authors.add(authorRequestToAuthor(it)));
        List<Category> categories = new LinkedList<>();
        addBookRequest.getCategories().forEach(it -> categories.add(categoryRequestToCategory(it)));

        book.setAuthors(authors);
        book.setCategories(categories);
        book.setLanguage(languageRequestToLanguage(addBookRequest.getLanguage()));
        book.setCondition(BookCondition.values()[addBookRequest.getConditionId()]);
        BeanUtils.copyProperties(addBookRequest, book, "authors", "image", "categories", "language");
        return book;
    }

    public static Category categoryRequestToCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryRequest, category);
        return category;
    }

    public static Author authorRequestToAuthor(AuthorRequest authorRequest) {
        Author author = new Author();
        BeanUtils.copyProperties(authorRequest, author);
        return author;
    }

    public static Coordinates coordinatesRequestToCoordinates(CoordinatesRequest coordinatesRequest) {

        Coordinates coordinates = new Coordinates();
        BeanUtils.copyProperties(coordinatesRequest, coordinates);
        return coordinates;
    }

    public static Language languageRequestToLanguage(LanguageRequest languageRequest) {
        Language language = new Language();
        BeanUtils.copyProperties(languageRequest, language);
        return language;
    }

    public static User userRequestToUser(UserRequest userRequest) {
        User user = new User();
        user.setCoordinates(coordinatesRequestToCoordinates(userRequest.getCoordinates()));
        BeanUtils.copyProperties(userRequest, user);
        return user;
    }

    public static Exchange addExchangeRequestToExchange(AddExchangeRequest addExchangeRequest) {
        Exchange exchange = new Exchange();
        exchange.setCoordinates(
                coordinatesRequestToCoordinates(addExchangeRequest.getCoordinates())
        );
        BeanUtils.copyProperties(addExchangeRequest, exchange, "authors");
        return exchange;
    }
}

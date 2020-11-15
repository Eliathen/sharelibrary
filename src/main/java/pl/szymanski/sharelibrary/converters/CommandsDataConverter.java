package pl.szymanski.sharelibrary.converters;

import org.springframework.beans.BeanUtils;
import pl.szymanski.sharelibrary.commanddata.AddBookCommandData;
import pl.szymanski.sharelibrary.commanddata.AddressCommandData;
import pl.szymanski.sharelibrary.commanddata.AuthorCommandData;
import pl.szymanski.sharelibrary.commanddata.UserCommandData;
import pl.szymanski.sharelibrary.entity.*;

import java.util.LinkedList;
import java.util.List;

public class CommandsDataConverter {

    public static Book AddBookCommandDataToBook(AddBookCommandData addBookCommandData) {
        Book book = new Book();
        List<Author> authors = new LinkedList<>();
        addBookCommandData.getAuthors().forEach(it -> authors.add(AuthorCommandDataToAuthor(it)));
        book.setAuthors(authors);
        BeanUtils.copyProperties(addBookCommandData, book, "authors", "image");
        return book;
    }

    public static Author AuthorCommandDataToAuthor(AuthorCommandData authorCommandData) {
        Author author = new Author();
        BeanUtils.copyProperties(authorCommandData, author);
        return author;
    }

    public static Address AddressCommandDataToAddress(AddressCommandData addressCommandData) {

        Address address = new Address();
        address.setCoordinates(new Coordinates());
        BeanUtils.copyProperties(addressCommandData, address, "coordinates");
        return address;
    }

    public static User UserCommandDataToUser(UserCommandData userCommandData) {
        User user = new User();
        user.setDefaultAddress(AddressCommandDataToAddress(userCommandData.getDefaultAddress()));
        BeanUtils.copyProperties(userCommandData, user, "defaultAddress");
        return user;
    }


}

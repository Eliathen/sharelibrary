package pl.szymanski.sharelibrary.converters;

import org.springframework.beans.BeanUtils;
import pl.szymanski.sharelibrary.commanddata.AddBookCommandData;
import pl.szymanski.sharelibrary.commanddata.AuthorCommandData;
import pl.szymanski.sharelibrary.entity.Author;
import pl.szymanski.sharelibrary.entity.Book;

import java.util.LinkedList;
import java.util.List;

public class CommandsDataConverter {

    public static Book AddBookCommandDataToBook(AddBookCommandData addBookCommandData) {
        Book book = new Book();
        List<Author> authors = new LinkedList<>();
        addBookCommandData.getAuthors().forEach(it -> {
            authors.add(AuthorCommandDataToAuthor(it));
        });
        book.setAuthors(authors);
        BeanUtils.copyProperties(addBookCommandData, book, "authors");
        return book;
    }

    public static Author AuthorCommandDataToAuthor(AuthorCommandData authorCommandData) {
        Author author = new Author();
        BeanUtils.copyProperties(authorCommandData, author);
        return author;
    }

}
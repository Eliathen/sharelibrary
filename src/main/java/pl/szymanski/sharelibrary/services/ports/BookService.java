package pl.szymanski.sharelibrary.services.ports;

import pl.szymanski.sharelibrary.entity.Author;
import pl.szymanski.sharelibrary.entity.Book;

import java.util.List;

public interface BookService {

    Book saveBook(Book book);

    Book findBookById(Long id);

    List<Book> getBooks();

    List<Book> getBooksByAuthorNameAndSurname(Author author);

    List<Book> getBooksByTitle(String title);

}

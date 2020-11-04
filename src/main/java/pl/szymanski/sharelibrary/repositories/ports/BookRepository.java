package pl.szymanski.sharelibrary.repositories.ports;

import pl.szymanski.sharelibrary.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    List<Book> getBooks();

    Book saveBook(Book book);

    Optional<Book> findBookById(Long id);

    List<Book> findBooksByTitle(String title);
}

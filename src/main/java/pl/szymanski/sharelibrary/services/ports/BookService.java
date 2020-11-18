package pl.szymanski.sharelibrary.services.ports;

import org.springframework.web.multipart.MultipartFile;
import pl.szymanski.sharelibrary.entity.Author;
import pl.szymanski.sharelibrary.entity.Book;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface BookService {

    Book findBookById(Long id);

    List<Book> getBooks(String query);

    List<Book> getBooksByAuthorNameAndSurname(Author author);

    List<Book> getBooksByTitle(String title);

    Book saveBook(Book book, MultipartFile cover, Long userId) throws IOException;

    Set<Book> findBooksByUserId(Long userId);
}

package pl.szymanski.sharelibrary.services.ports;

import org.springframework.web.multipart.MultipartFile;
import pl.szymanski.sharelibrary.entity.Author;
import pl.szymanski.sharelibrary.entity.Book;
import pl.szymanski.sharelibrary.views.UserBookResponse;

import java.io.IOException;
import java.util.List;

public interface BookService {

    Book findBookById(Long id);

    List<Book> getBooks(String query);

    List<Book> getBooksByAuthorNameAndSurname(Author author);

    List<Book> getBooksByTitle(String title);

    Book saveBook(Book book, MultipartFile cover, Long userId) throws IOException;

    List<UserBookResponse> findBooksByUserId(Long userId);
}

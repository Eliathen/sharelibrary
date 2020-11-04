package pl.szymanski.sharelibrary.services.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szymanski.sharelibrary.entity.Author;
import pl.szymanski.sharelibrary.entity.Book;
import pl.szymanski.sharelibrary.exceptions.BookDoesNotExist;
import pl.szymanski.sharelibrary.repositories.ports.AuthorRepository;
import pl.szymanski.sharelibrary.repositories.ports.BookRepository;
import pl.szymanski.sharelibrary.services.ports.BookService;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public Book saveBook(Book book) {
        List<Author> authors = book.getAuthors().stream().map(it ->
                authorRepository.findAuthorByNameAndSurname(it.getName(), it.getSurname()).orElse(it)
        ).collect(Collectors.toList());
        book.setAuthors(authors);
        return bookRepository.saveBook(book);
    }

    @Override
    public Book findBookById(Long id) {
        return bookRepository.findBookById(id).orElseThrow(() -> new BookDoesNotExist(id));
    }

    @Override
    public List<Book> getBooks() {
        return bookRepository.getBooks();
    }

    @Override
    public List<Book> getBooksByAuthorNameAndSurname(Author author) {
        return authorRepository.findAuthorByNameAndSurname(author.getName(), author.getSurname())
                .map(Author::getBooks).orElse(new LinkedList<>());
    }

    @Override
    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findBooksByTitle(title);
    }

}

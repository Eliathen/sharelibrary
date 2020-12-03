package pl.szymanski.sharelibrary.services.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.szymanski.sharelibrary.entity.*;
import pl.szymanski.sharelibrary.exceptions.books.BookDoesNotExist;
import pl.szymanski.sharelibrary.repositories.ports.AuthorRepository;
import pl.szymanski.sharelibrary.repositories.ports.BookRepository;
import pl.szymanski.sharelibrary.response.UserBookResponse;
import pl.szymanski.sharelibrary.services.ports.BookService;
import pl.szymanski.sharelibrary.services.ports.UserService;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final UserService userService;

    @Override
    public Book findBookById(Long id) {
        return bookRepository.getBookById(id).orElseThrow(() -> new BookDoesNotExist(id));
    }

    @Override
    public List<Book> getBooks(String query) {
        Set<Book> books = new HashSet<>(bookRepository.findBooksByTitle(query));
        List<String> queries = Arrays.asList(query.split(" "));
        queries.forEach(it -> books.addAll(bookRepository.findBooksByTitle(it)));
        queries.forEach(it -> authorRepository.findAuthorByNameAndSurname(it, it).map(author ->
                        author.getBooks().stream().map(books::add)
                )
        );
        return new ArrayList<>(books);
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

    @Override
    public Book saveBook(Book book, MultipartFile cover, Long userId) throws IOException {
        book.setTitle(book.getTitle().replace("\"", ""));
        if (!Objects.isNull(cover)) {
            List<Cover> covers = new LinkedList<>();
            covers.add(getCoverFromMultipartFile(cover));
            book.setCover(covers);
        }
        List<Author> authors = book.getAuthors().stream().map(it ->
                authorRepository.findAuthorByNameAndSurname(it.getName(), it.getSurname()).orElse(it)
        ).collect(Collectors.toList());
        book.setAuthors(authors);
        Book newBook = bookRepository.saveBook(book);
        userService.assignBookToUser(userId, newBook.getId());
        return newBook;
    }

    @Override
    public List<UserBookResponse> findBooksByUserId(Long userId) {
        User user = userService.getUserById(userId);
        List<UserBook> books = user.getBooks();
        List<UserBookResponse> response = new LinkedList<>();
        books.forEach(book -> response.add(UserBookResponse.of(book)));
        return response;
    }

    private Cover getCoverFromMultipartFile(MultipartFile cover) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(cover.getOriginalFilename()));
        return new Cover(
                fileName,
                cover.getContentType(),
                cover.getBytes()
        );
    }

}

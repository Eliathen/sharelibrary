package pl.szymanski.sharelibrary.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.szymanski.sharelibrary.commanddata.AddBookCommandData;
import pl.szymanski.sharelibrary.commanddata.AuthorCommandData;
import pl.szymanski.sharelibrary.converters.CommandsDataConverter;
import pl.szymanski.sharelibrary.entity.Author;
import pl.szymanski.sharelibrary.entity.Book;
import pl.szymanski.sharelibrary.services.ports.BookService;
import pl.szymanski.sharelibrary.views.BookWithoutUsersView;

import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/{bookId}")
    public ResponseEntity<BookWithoutUsersView> getBookById(@PathVariable Long bookId) {
        return new ResponseEntity<>(BookWithoutUsersView.of(bookService.findBookById(bookId)), OK);
    }

    @GetMapping
    public ResponseEntity<Set<BookWithoutUsersView>> getBooks() {
        return new ResponseEntity<>(bookService.getBooks().stream().map(BookWithoutUsersView::of).collect(Collectors.toSet()), OK);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<BookWithoutUsersView> saveBook(@RequestBody AddBookCommandData addBookCommandData) {
        Book book = CommandsDataConverter.AddBookCommandDataToBook(addBookCommandData);
        return new ResponseEntity<>(BookWithoutUsersView.of(bookService.saveBook(book)), OK);
    }

    @GetMapping("/author")
    public ResponseEntity<Set<BookWithoutUsersView>> getBooksByAuthor(@RequestBody AuthorCommandData authorCommandData) {
        Author author = CommandsDataConverter.AuthorCommandDataToAuthor(authorCommandData);
        return new ResponseEntity<>(
                bookService.getBooksByAuthorNameAndSurname(author)
                        .stream()
                        .map(BookWithoutUsersView::of)
                        .collect(Collectors.toSet()),
                OK);
    }

    @GetMapping("/title")
    public ResponseEntity<Set<BookWithoutUsersView>> getBooksByTitle(@RequestParam String title) {
        return new ResponseEntity<>(
                bookService.getBooksByTitle(title)
                        .stream()
                        .map(BookWithoutUsersView::of)
                        .collect(Collectors.toSet()),
                OK);
    }
}

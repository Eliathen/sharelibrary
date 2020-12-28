package pl.szymanski.sharelibrary.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.szymanski.sharelibrary.converters.RequestConverter;
import pl.szymanski.sharelibrary.entity.Author;
import pl.szymanski.sharelibrary.entity.Cover;
import pl.szymanski.sharelibrary.requests.AddBookRequest;
import pl.szymanski.sharelibrary.requests.AuthorRequest;
import pl.szymanski.sharelibrary.response.BookWithoutUsersResponse;
import pl.szymanski.sharelibrary.response.UserBookResponse;
import pl.szymanski.sharelibrary.services.ports.BookService;
import pl.szymanski.sharelibrary.services.ports.CoverService;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final CoverService coverService;

    @GetMapping("/{bookId}")
    public ResponseEntity<BookWithoutUsersResponse> getBookById(@PathVariable Long bookId) {
        return new ResponseEntity<>(BookWithoutUsersResponse.of(bookService.findBookById(bookId)), OK);
    }

    @GetMapping("/{bookId}/cover")
    public ResponseEntity<byte[]> getCoverByBookId(@PathVariable Long bookId) {
        Cover cover = coverService.getCoverByBookId(bookId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, cover.getType())
                .body(cover.getData());
    }

    @GetMapping
    public ResponseEntity<Set<BookWithoutUsersResponse>> searchBooks(@RequestParam(name = "q") String query) {
        return new ResponseEntity<>(bookService.getBooks(query).stream().map(BookWithoutUsersResponse::of).collect(Collectors.toSet()), OK);
    }


    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @Transactional
    public ResponseEntity<BookWithoutUsersResponse> saveBook(@ModelAttribute AddBookRequest book, MultipartFile image, Long userId) throws IOException {
        return new ResponseEntity<>(BookWithoutUsersResponse.of(bookService.saveBook(RequestConverter.addBookRequestToBook(book), image, userId)), CREATED);
    }


    @GetMapping("/author")
    public ResponseEntity<Set<BookWithoutUsersResponse>> getBooksByAuthor(@RequestBody AuthorRequest authorRequest) {
        Author author = RequestConverter.authorRequestToAuthor(authorRequest);
        return new ResponseEntity<>(
                bookService.getBooksByAuthorNameAndSurname(author)
                        .stream()
                        .map(BookWithoutUsersResponse::of)
                        .collect(Collectors.toSet()),
                OK);
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<List<UserBookResponse>> getUsersBooks(@PathVariable Long userId) {
        return new ResponseEntity<>(
                bookService.findBooksByUserId(userId),
                OK
        );
    }

    @GetMapping("/title")
    public ResponseEntity<Set<BookWithoutUsersResponse>> getBooksByTitle(@RequestParam String title) {
        return new ResponseEntity<>(
                bookService.getBooksByTitle(title)
                        .stream()
                        .map(BookWithoutUsersResponse::of)
                        .collect(Collectors.toSet()),
                OK);
    }

}

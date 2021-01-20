package pl.szymanski.sharelibrary.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.szymanski.sharelibrary.converters.RequestConverter;
import pl.szymanski.sharelibrary.entity.Cover;
import pl.szymanski.sharelibrary.requests.AddBookRequest;
import pl.szymanski.sharelibrary.response.BookWithoutUsersResponse;
import pl.szymanski.sharelibrary.response.LanguageResponse;
import pl.szymanski.sharelibrary.response.UserBookResponse;
import pl.szymanski.sharelibrary.response.UserResponse;
import pl.szymanski.sharelibrary.services.ports.BookService;
import pl.szymanski.sharelibrary.services.ports.CoverService;
import pl.szymanski.sharelibrary.services.ports.UserService;

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
    private final UserService userService;

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

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    @Transactional
    public ResponseEntity<BookWithoutUsersResponse> saveBook(
            @ModelAttribute AddBookRequest book,
            MultipartFile image,
            Long userId) throws IOException {
        return new ResponseEntity<>(BookWithoutUsersResponse.of(bookService.saveBook(RequestConverter.addBookRequestToBook(book), image, userId)), CREATED);
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<List<UserBookResponse>> getUsersBooks(@PathVariable Long userId) {
        return new ResponseEntity<>(
                bookService.findBooksByUserId(userId),
                OK
        );
    }

    @GetMapping("/languages")
    public ResponseEntity<Set<LanguageResponse>> getLanguages() {
        return new ResponseEntity<>(
                bookService.getLanguages().stream().map(LanguageResponse::of).collect(Collectors.toSet()), OK
        );
    }

    //get list of user with books which user of id userId have
    @GetMapping("/{userId}/exchanged")
    public ResponseEntity<List<UserResponse>> getExchangedBooks(@PathVariable Long userId) {
        return new ResponseEntity<>(
                userService.getUsersWithBooksWhereAtUserIs(userId).stream().map(UserResponse::of).collect(Collectors.toList()),
                OK
        );
    }

}

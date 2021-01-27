package pl.szymanski.sharelibrary.repositories.adapters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.szymanski.sharelibrary.entity.Book;
import pl.szymanski.sharelibrary.entity.Cover;
import pl.szymanski.sharelibrary.repositories.jpa.BookJPARepository;
import pl.szymanski.sharelibrary.repositories.jpa.CoverJPARepository;
import pl.szymanski.sharelibrary.utils.generator.BookGenerator;
import pl.szymanski.sharelibrary.utils.generator.CoverGenerator;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class BookRepositoryImplTest {

    @Autowired
    BookJPARepository bookJPARepository;


    @Autowired
    CoverJPARepository coverJPARepository;

    @Autowired
    BookRepositoryImpl bookRepository;

    @BeforeEach
    void setUp() {
        bookJPARepository.deleteAll();
        coverJPARepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        bookJPARepository.deleteAll();
        coverJPARepository.deleteAll();
    }

    @Test
    void shouldAddBook() {
        Book book = BookGenerator.getBook();
        book.setCover(List.of(CoverGenerator.getCover()));
        Book result = bookRepository.saveBook(book);
        Assertions.assertThat(result.getCondition()).isEqualTo(book.getCondition());
        Assertions.assertThat(result.getTitle()).isEqualTo(book.getTitle());
        Assertions.assertThat(result.getLanguage().getName()).isEqualTo(book.getLanguage().getName());
        for (int i = 0; i < result.getAuthors().size(); i++) {
            Assertions.assertThat(result.getAuthors().get(i).getName()).isEqualTo(book.getAuthors().get(i).getName());
            Assertions.assertThat(result.getAuthors().get(i).getSurname()).isEqualTo(book.getAuthors().get(i).getSurname());
        }

    }

    @Test
    void shouldReturnBookByGivenId() {
        Book book = BookGenerator.getBook();
        Cover cover = CoverGenerator.getCover();
        cover.setBook(book);
        book.setCover(List.of(cover));
        Long bookId = bookJPARepository.saveAndFlush(book).getId();
        Optional<Book> result = bookRepository.getBookById(bookId);
        Assertions.assertThat(result.isPresent()).isTrue();
        Assertions.assertThat(result.get().getId()).isEqualTo(bookId);
    }

    @Test
    void shouldReturnListOfBookByGivenTitle() {
        Book book = BookGenerator.getBook();
        Book book1 = BookGenerator.getBook();

        Cover cover = CoverGenerator.getCover();
        cover.setBook(book);
        book.setCover(List.of(cover));
        Cover cover1 = CoverGenerator.getCover();
        cover1.setBook(book1);
        book1.setCover(List.of(cover1));
        String bookTitle = bookJPARepository.saveAndFlush(book).getTitle();
        bookJPARepository.saveAndFlush(book1);
        List<Book> result = bookRepository.findBooksByTitle(bookTitle);
        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result.stream().findFirst().get().getTitle()).isEqualTo(bookTitle);
    }
}
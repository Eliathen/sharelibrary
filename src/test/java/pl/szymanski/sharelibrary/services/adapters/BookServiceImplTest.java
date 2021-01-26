package pl.szymanski.sharelibrary.services.adapters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import pl.szymanski.sharelibrary.entity.Book;
import pl.szymanski.sharelibrary.entity.Language;
import pl.szymanski.sharelibrary.entity.User;
import pl.szymanski.sharelibrary.exceptions.books.BookDoesNotExist;
import pl.szymanski.sharelibrary.repositories.ports.AuthorRepository;
import pl.szymanski.sharelibrary.repositories.ports.BookRepository;
import pl.szymanski.sharelibrary.repositories.ports.LanguageRepository;
import pl.szymanski.sharelibrary.response.UserBookResponse;
import pl.szymanski.sharelibrary.services.ports.CategoryService;
import pl.szymanski.sharelibrary.services.ports.UserService;
import pl.szymanski.sharelibrary.utils.generator.BookGenerator;
import pl.szymanski.sharelibrary.utils.generator.LanguageGenerator;
import pl.szymanski.sharelibrary.utils.generator.UserGenerator;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    BookRepository bookRepository;
    @Mock
    AuthorRepository authorRepository;
    @Mock
    LanguageRepository languageRepository;
    @Mock
    UserService userService;
    @Mock
    CategoryService categoryService;

    @InjectMocks
    BookServiceImpl bookService;

    @Test
    void shouldReturnBook() {
        //given
        Book book = BookGenerator.getBook();
        when(bookRepository.getBookById(1L)).
                thenReturn(Optional.of(book));
        //when
        Book result = bookService.findBookById(1L);
        //then
        Assertions.assertThat(result.getId()).isEqualTo(book.getId());
    }

    @Test
    void shouldThrowExceptionWhenBookDoesNotExist() {
        //given
        Book book = BookGenerator.getBook();
        when(bookRepository.getBookById(1L)).
                thenReturn(Optional.empty());
        //when & then
        Assertions.assertThatThrownBy(() ->
                bookService.findBookById(1L)
        ).isInstanceOf(BookDoesNotExist.class);
    }

    @Test
    void shouldSaveAndReturnBook() throws IOException {
        //given
        Book book = BookGenerator.getBook();
        MultipartFile cover = new MockMultipartFile(
                "test Photo",
                null,
                "image/jpeg",
                new byte[]{'z'}
        );
        User user = UserGenerator.getUser();
        when(authorRepository.findAuthorByNameAndSurname(any(), any()))
                .thenReturn(book.getAuthors().stream().findFirst());
        when(categoryService.findByName(any())).thenReturn(book.getCategories().stream().findFirst().get());
        when(userService.assignBookToUser(any(), any()))
                .thenReturn(user);
        when(languageRepository.getLanguageById(1)).thenReturn(
                Optional.of(LanguageGenerator.getLanguage())
        );
        when(bookRepository.saveBook(any())).thenReturn(book);
        ArgumentCaptor<Book> argumentCaptor = ArgumentCaptor.forClass(Book.class);
        //when
        Book result = bookService.saveBook(book, cover, user.getId());
        //then
        verify(bookRepository).saveBook(argumentCaptor.capture());
        Assertions.assertThat(argumentCaptor.getValue().getTitle()).isEqualTo(book.getTitle());
        Assertions.assertThat(argumentCaptor.getValue().getCondition()).isEqualTo(book.getCondition());
        Assertions.assertThat(argumentCaptor.getValue().getCategories()).isEqualTo(book.getCategories());
        Assertions.assertThat(argumentCaptor.getValue().getLanguage()).isEqualTo(book.getLanguage());
        Assertions.assertThat(argumentCaptor.getValue().getAuthors()).isEqualTo(book.getAuthors());


        Assertions.assertThat(result.getTitle()).isEqualTo(book.getTitle());
        Assertions.assertThat(result.getCondition()).isEqualTo(book.getCondition());
        Assertions.assertThat(result.getCategories()).isEqualTo(book.getCategories());
        Assertions.assertThat(result.getLanguage()).isEqualTo(book.getLanguage());
        Assertions.assertThat(result.getAuthors()).isEqualTo(book.getAuthors());
    }

    @Test
    void shouldReturnListOfBooksWithOneElementByUserId() {
        //given
        User user = UserGenerator.getUser();
        user.setBooks(List.of(BookGenerator.getUserBook()));
        when(userService.getUserById(user.getId())).thenReturn(user);
        //when
        List<UserBookResponse> result = bookService.findBooksByUserId(user.getId());
        //then
        Assertions.assertThat(result).hasSize(1);
    }

    @Test
    void shouldReturnSetOfLanguagesWithOneElement() {
        //given
        Language language = LanguageGenerator.getLanguage();
        when(languageRepository.getAll()).thenReturn(Set.of(language));
        //when
        Set<Language> result = bookService.getLanguages();
        //then
        Assertions.assertThat(result).hasSize(1);
    }

}
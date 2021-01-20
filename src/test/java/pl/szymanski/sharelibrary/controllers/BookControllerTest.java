package pl.szymanski.sharelibrary.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.szymanski.sharelibrary.entity.Book;
import pl.szymanski.sharelibrary.entity.Cover;
import pl.szymanski.sharelibrary.entity.Language;
import pl.szymanski.sharelibrary.entity.User;
import pl.szymanski.sharelibrary.exceptions.books.BookDoesNotExist;
import pl.szymanski.sharelibrary.exceptions.covers.CoverForBookDoesNotExists;
import pl.szymanski.sharelibrary.response.BookWithoutUsersResponse;
import pl.szymanski.sharelibrary.response.UserBookResponse;
import pl.szymanski.sharelibrary.response.UserResponse;
import pl.szymanski.sharelibrary.services.ports.BookService;
import pl.szymanski.sharelibrary.services.ports.CoverService;
import pl.szymanski.sharelibrary.services.ports.UserService;
import pl.szymanski.sharelibrary.utils.generator.BookGenerator;
import pl.szymanski.sharelibrary.utils.generator.CoverGenerator;
import pl.szymanski.sharelibrary.utils.generator.LanguageGenerator;
import pl.szymanski.sharelibrary.utils.generator.UserGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@WithMockUser(username = "z", password = "z")
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;
    @MockBean
    private CoverService coverService;
    @MockBean
    private UserService userService;

    @Test
    void should_return_book_without_users_and_status_200() throws Exception {
        //given
        Book book = BookGenerator.getBook();
        when(bookService.findBookById(anyLong())).thenReturn(book);
        //when
        MvcResult result = mockMvc.perform(get("/api/v1/books/" + book.getId())
        ).andExpect(status().isOk()).andReturn();
        //then
        BookWithoutUsersResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), BookWithoutUsersResponse.class);
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getId()).isEqualTo(book.getId());
    }

    @Test
    void should_throw_book_not_exists_and_status_404() throws Exception {
        //given
        long bookId = 1L;
        when(bookService.findBookById(anyLong())).thenThrow(new BookDoesNotExist(bookId));
        //when
        MvcResult result = mockMvc.perform(get("/api/v1/books/" + bookId)
        ).andExpect(status().isNotFound()).andReturn();
        //then
        assertTrue(result.getResolvedException() instanceof BookDoesNotExist);
    }

    @Test
    void should_return_cover_data_and_status_200() throws Exception {
        //given
        Cover cover = CoverGenerator.getCover();
        long bookId = 1L;
        when(coverService.getCoverByBookId(anyLong())).thenReturn(cover);
        //when
        MvcResult result = mockMvc.perform(get("/api/v1/books/" + bookId + "/cover")
        ).andExpect(status().isOk()).andReturn();
        //then
        Assertions.assertThat(result.getResponse().getContentAsByteArray()
        ).isNotEmpty();
    }

    @Test
    void should_throw_cover_for_book_does_not_exists_and_return_status_404() throws Exception {
        //given
        Cover cover = CoverGenerator.getCover();
        long bookId = 1L;
        when(coverService.getCoverByBookId(bookId)).thenThrow(new CoverForBookDoesNotExists(bookId));
        //when
        MvcResult result = mockMvc.perform(
                get("/api/v1/books/" + bookId + "/cover")
        ).andExpect(status().isNotFound())
                .andReturn();
        //then
        assertTrue(result.getResolvedException() instanceof CoverForBookDoesNotExists);
    }


    @Test
    void should_return_set_of_language_response_and_status_200() throws Exception {
        //given
        Set<Language> languageSet = Set.of(
                LanguageGenerator.getLanguage()
        );
        when(bookService.getLanguages()).thenReturn(languageSet);
        //when
        MvcResult result = mockMvc.perform(
                get("/api/v1/books/languages")
        ).andExpect(status().isOk())
                .andReturn();
        //then
        List<Language> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );
        Assertions.assertThat(response).isNotEmpty();
    }

    @Test
    void should_return_list_of_books_and_status_200() throws Exception {
        //given
        Book book = BookGenerator.getBook();
        book.setTitle("New lord");
        List<Book> books = Arrays.asList(
                BookGenerator.getBook(),
                book
        );
        String firstQuery = "lord";
        String secondQuery = "new";
        String thirdQuery = "xyz";
        when(bookService.getBooks(firstQuery)).thenReturn(books);
        when(bookService.getBooks(secondQuery)).thenReturn(List.of(book));
        when(bookService.getBooks(thirdQuery)).thenReturn(new ArrayList());

        //when
        MvcResult firstResult = mockMvc.perform(get("/api/v1/books")
                .param("q", firstQuery)
        ).andExpect(status().isOk()).andReturn();
        MvcResult secondResult = mockMvc.perform(get("/api/v1/books")
                .param("q", secondQuery)
        ).andExpect(status().isOk()).andReturn();
        MvcResult thirdResult = mockMvc.perform(get("/api/v1/books")
                .param("q", thirdQuery)
        ).andExpect(status().isOk()).andReturn();
        //then
        List<BookWithoutUsersResponse> firstResponse = objectMapper.readValue(
                firstResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );
        List<BookWithoutUsersResponse> secondResponse = objectMapper.readValue(
                secondResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );
        List<BookWithoutUsersResponse> thirdResponse = objectMapper.readValue(
                thirdResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );
        Assertions.assertThat(firstResponse.size()).isEqualTo(books.size());
        Assertions.assertThat(secondResponse.size()).isEqualTo(1);
        Assertions.assertThat(thirdResponse).isEmpty();
    }

    @Test
    void should_return_book_after_save_and_status_201() throws Exception {
        //given
        Book book = BookGenerator.getBook();
        when(bookService.saveBook(any(), any(), anyLong())).thenReturn(book);
        Cover cover = CoverGenerator.getCover();
        Long userId = 1L;
        MockMultipartFile request = new MockMultipartFile(
                "image",
                cover.getName(),
                "image/*",
                cover.getData()
        );
        //when
        MvcResult result = mockMvc.perform(multipart("/api/v1/books")
                .file(request)
                .param("authors[0].name", book.getAuthors().get(0).getName())
                .param("authors[0].surname", book.getAuthors().get(0).getSurname())
                .param("categories[0].id", String.valueOf(book.getCategories().get(0).getId()))
                .param("categories[0].name", book.getCategories().get(0).getName())
                .param("language.id", String.valueOf(book.getLanguage().getId()))
                .param("language.name", book.getLanguage().getName())
                .param("conditionId", String.valueOf(book.getCondition().ordinal()))
                .param("userId", String.valueOf(userId))
                .param("title", book.getTitle())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated())
                .andReturn();
        BookWithoutUsersResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), BookWithoutUsersResponse.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getId()).isEqualTo(book.getId());
        Assertions.assertThat(response.getCategories().size()).isEqualTo(book.getCategories().size());
        Assertions.assertThat(response.getAuthors().size()).isEqualTo(book.getAuthors().size());
        Assertions.assertThat(response.getCondition()).isEqualTo(book.getCondition());
        Assertions.assertThat(response.getLanguage().getId()).isEqualTo(book.getLanguage().getId());

        //then
    }

    @Test
    void should_return_list_of_user_books_and_status_200() throws Exception {
        //given
        List<UserBookResponse> books = Stream.of(
                BookGenerator.getUserBook(), BookGenerator.getUserBook()).map(UserBookResponse::of).collect(Collectors.toList()
        );
        when(bookService.findBooksByUserId(anyLong())).thenReturn(books);
        long userId = 1L;
        //when
        MvcResult result = mockMvc.perform(
                get("/api/v1/books/user/" + userId)
        ).andExpect(status().isOk())
                .andReturn();
        //then
        List<UserBookResponse> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );
        Assertions.assertThat(response).isNotEmpty();
    }

    @Test
    void should_return_empty_list_of_user_books_and_status_200() throws Exception {
        //given
        when(bookService.findBooksByUserId(anyLong())).thenReturn(new ArrayList<>());
        long userId = 1L;
        //when
        MvcResult result = mockMvc.perform(
                get("/api/v1/books/user/" + userId)
        ).andExpect(status().isOk())
                .andReturn();
        //then
        List<UserBookResponse> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );
        Assertions.assertThat(response).isEmpty();
    }

    @Test
    void should_return_list_of_users_with_books_which_have_user_of_id_and_status_200() throws Exception {
        //given
        List<User> users = Arrays.asList(
                UserGenerator.getUserWithBooks(),
                UserGenerator.getUserWithBooks()
        );
        long userId = 1L;
        when(userService.getUsersWithBooksWhereAtUserIs(userId)).thenReturn(users);
        //when
        MvcResult result = mockMvc.perform(
                get("/api/v1/books/" + userId + "/exchanged")
        ).andExpect(status().isOk())
                .andReturn();
        //then
        List<UserResponse> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );
        Assertions.assertThat(response).isNotEmpty();
    }
}
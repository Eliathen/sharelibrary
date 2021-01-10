package pl.szymanski.sharelibrary.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.szymanski.sharelibrary.entity.Book;
import pl.szymanski.sharelibrary.entity.Cover;
import pl.szymanski.sharelibrary.entity.Language;
import pl.szymanski.sharelibrary.exceptions.books.BookDoesNotExist;
import pl.szymanski.sharelibrary.exceptions.covers.CoverForBookDoesNotExists;
import pl.szymanski.sharelibrary.response.BookWithoutUsersResponse;
import pl.szymanski.sharelibrary.services.ports.BookService;
import pl.szymanski.sharelibrary.services.ports.CoverService;
import pl.szymanski.sharelibrary.services.ports.UserService;
import pl.szymanski.sharelibrary.utils.generator.BookGenerator;
import pl.szymanski.sharelibrary.utils.generator.CoverGenerator;
import pl.szymanski.sharelibrary.utils.generator.LanguageGenerator;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

}
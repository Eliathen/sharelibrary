package pl.szymanski.sharelibrary.services.adapters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.szymanski.sharelibrary.entity.Book;
import pl.szymanski.sharelibrary.entity.Cover;
import pl.szymanski.sharelibrary.services.ports.BookService;
import pl.szymanski.sharelibrary.utils.generator.BookGenerator;
import pl.szymanski.sharelibrary.utils.generator.CoverGenerator;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CoverServiceImplTest {

    @Mock
    BookService bookService;

    @InjectMocks
    CoverServiceImpl coverService;

    @Test
    void shouldReturnCoverByBookId() {
        //given
        Book book = BookGenerator.getBook();
        book.setCover(List.of(CoverGenerator.getCover()));
        when(bookService.findBookById(book.getId())).thenReturn(book);
        //when
        Cover result = coverService.getCoverByBookId(book.getId());
        //then
        Assertions.assertThat(result.getId()).isEqualTo(book.getCover().stream().findFirst().get().getId());
    }

}
package pl.szymanski.sharelibrary.repositories.adapters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.szymanski.sharelibrary.entity.Author;
import pl.szymanski.sharelibrary.entity.Book;
import pl.szymanski.sharelibrary.repositories.jpa.AuthorJPARepository;
import pl.szymanski.sharelibrary.repositories.jpa.BookJPARepository;
import pl.szymanski.sharelibrary.repositories.ports.AuthorRepository;
import pl.szymanski.sharelibrary.utils.generator.AuthorGenerator;
import pl.szymanski.sharelibrary.utils.generator.BookGenerator;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;

@SpringBootTest
class AuthorRepositoryImplTest {

    @Autowired
    private BookJPARepository bookJPARepository;
    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        bookJPARepository.deleteAll();
    }

    @AfterEach
    void cleanUp() {
        bookJPARepository.deleteAll();
    }

    @Test
    void shouldFindAuthorByNameAndSurname() {
        //given
        Author author = AuthorGenerator.getAuthor();
        Book in = BookGenerator.getBook();
        bookJPARepository.saveAndFlush(in);
        //when
        Optional<Author> out = authorRepository.findAuthorByNameAndSurname(author.getName(), author.getSurname());
        //then
        Assertions.assertThat(out).isPresent().containsInstanceOf(Author.class);
    }

    @Test
    void shouldFindAuthorByNameOrSurname() {
        //given
        Author author = AuthorGenerator.getAuthor();
        Book in = BookGenerator.getBook();
        bookJPARepository.saveAndFlush(in);
        //when
        List<Author> out = authorRepository.findAuthorByNameOrSurname(author.getName(), author.getSurname());
        //then
        Assertions.assertThat(out).isNotEmpty();
        Assertions.assertThat(out.size()).isEqualTo(1);
    }

    @Test
    void shouldCallMethodFindByNameOrSurnameFromJpaRepository() {
        //given
        Author author = AuthorGenerator.getAuthor();
        AuthorJPARepository authorJPARepository = Mockito.mock(AuthorJPARepository.class);
        AuthorRepository repository = new AuthorRepositoryImpl(authorJPARepository);
        //when
        List<Author> result = repository.findAuthorByNameOrSurname(author.getName(), author.getSurname());
        //then
        Mockito.verify(authorJPARepository, times(1))
                .findAuthorsByNameIgnoreCaseOrSurnameIgnoreCase(author.getName(), author.getSurname());
    }

    @Test
    void shouldCallMethodFindByNameAndSurnameFromJpaRepository() {
        //given
        Author author = AuthorGenerator.getAuthor();
        AuthorJPARepository authorJPARepository = Mockito.mock(AuthorJPARepository.class);
        AuthorRepository repository = new AuthorRepositoryImpl(authorJPARepository);
        //when
        Optional<Author> result = repository.findAuthorByNameAndSurname(author.getName(), author.getSurname());
        //then
        Mockito.verify(authorJPARepository, times(1)).findAuthorByNameIgnoreCaseAndSurnameIgnoreCase(author.getName(), author.getSurname());
    }
}
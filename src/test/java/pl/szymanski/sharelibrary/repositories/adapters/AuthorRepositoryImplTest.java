package pl.szymanski.sharelibrary.repositories.adapters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.szymanski.sharelibrary.entity.Author;
import pl.szymanski.sharelibrary.entity.Book;
import pl.szymanski.sharelibrary.repositories.jpa.BookJPARepository;
import pl.szymanski.sharelibrary.repositories.jpa.ExchangeJPARepository;
import pl.szymanski.sharelibrary.repositories.ports.AuthorRepository;
import pl.szymanski.sharelibrary.utils.generator.BookGenerator;

import java.util.Optional;

import static pl.szymanski.sharelibrary.utils.generator.AuthorGenerator.getAuthor;

@SpringBootTest
class AuthorRepositoryImplTest {

    @Autowired
    private BookJPARepository bookJPARepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    ExchangeJPARepository exchangeJPARepository;

    @BeforeEach
    void setUp() {
        exchangeJPARepository.deleteAll();
        bookJPARepository.deleteAll();
    }

    @AfterEach
    void cleanUp() {
        exchangeJPARepository.deleteAll();
        bookJPARepository.deleteAll();
    }

    @Test
    void should_find_author_by_name_and_surname() {
        //given
        Author author = getAuthor();
        Book in = BookGenerator.getBook();
        bookJPARepository.saveAndFlush(in);
        //when
        Optional<Author> out = authorRepository.findAuthorByNameAndSurname(author.getName(), author.getSurname());
        //then
        Assertions.assertThat(out).isPresent().containsInstanceOf(Author.class);
    }

}
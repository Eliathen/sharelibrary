package pl.szymanski.sharelibrary.repositories.adapters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.szymanski.sharelibrary.entity.Book;
import pl.szymanski.sharelibrary.entity.Exchange;
import pl.szymanski.sharelibrary.entity.User;
import pl.szymanski.sharelibrary.enums.ExchangeStatus;
import pl.szymanski.sharelibrary.repositories.jpa.BookJPARepository;
import pl.szymanski.sharelibrary.repositories.jpa.ExchangeJPARepository;
import pl.szymanski.sharelibrary.repositories.jpa.UserJPARepository;
import pl.szymanski.sharelibrary.utils.generator.BookGenerator;
import pl.szymanski.sharelibrary.utils.generator.ExchangeGenerator;
import pl.szymanski.sharelibrary.utils.generator.UserGenerator;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class ExchangeRepositoryImplTest {

    @Autowired
    ExchangeJPARepository exchangeJPARepository;
    @Autowired
    BookJPARepository bookJPARepository;
    @Autowired
    UserJPARepository userJPARepository;
    @Autowired
    ExchangeRepositoryImpl exchangeRepository;

    @BeforeEach
    void setUp() {
        exchangeJPARepository.deleteAll();
        bookJPARepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        exchangeJPARepository.deleteAll();
        bookJPARepository.deleteAll();
    }

    @Test
    void shouldSaveExchange() {
        Book book = bookJPARepository.save(BookGenerator.getBook());
        Exchange exchange = ExchangeGenerator.getExchange();
        exchange.setBook(book);
        User user = UserGenerator.getUser();
        user.setBooks(Collections.emptyList());
        exchange.setUser(userJPARepository.saveAndFlush(user));
        exchange.setForBook(null);
        exchange.setWithUser(null);
        Exchange result = exchangeRepository.saveExchange(exchange);
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    void shouldReturnBookByGivenId() {
        Book book = bookJPARepository.save(BookGenerator.getBook());
        Exchange exchange = ExchangeGenerator.getExchange();
        exchange.setBook(book);
        User user = UserGenerator.getUser();
        user.setBooks(Collections.emptyList());
        exchange.setUser(userJPARepository.saveAndFlush(user));
        exchange.setForBook(null);
        exchange.setWithUser(null);
        Long id = exchangeJPARepository.saveAndFlush(exchange).getId();
        Optional<Exchange> result = exchangeRepository.getExchangeById(id);
        Assertions.assertThat(result.isPresent()).isTrue();
        Assertions.assertThat(result.get().getId()).isEqualTo(id);

    }

    @Test
    void shouldReturnListOfExchangeWithOneElement() {
        Book book = bookJPARepository.save(BookGenerator.getBook());
        Exchange exchange = ExchangeGenerator.getExchange();
        exchange.setBook(book);
        User user = UserGenerator.getUser();
        user.setBooks(Collections.emptyList());
        exchange.setUser(userJPARepository.saveAndFlush(user));
        exchange.setForBook(null);
        exchange.setWithUser(null);
        Long id = exchangeJPARepository.saveAndFlush(exchange).getId();
        List<Exchange> result = exchangeRepository.getAll();
        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result.size()).isEqualTo(1);

    }

    @Test
    void shouldReturnListOfExchangeWithOneElementByGivenStatus() {
        Book book = bookJPARepository.save(BookGenerator.getBook());
        Exchange exchange = ExchangeGenerator.getExchange();
        exchange.setBook(book);
        User user = UserGenerator.getUser();
        user.setBooks(Collections.emptyList());
        exchange.setUser(userJPARepository.saveAndFlush(user));
        exchange.setForBook(null);
        exchange.setWithUser(null);
        exchangeJPARepository.saveAndFlush(exchange);
        List<Exchange> result = exchangeRepository.getExchangeByStatus(ExchangeStatus.STARTED);
        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result.size()).isEqualTo(1);
    }
}
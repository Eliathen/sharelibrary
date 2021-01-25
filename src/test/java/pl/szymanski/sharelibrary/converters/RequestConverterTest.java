package pl.szymanski.sharelibrary.converters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.szymanski.sharelibrary.entity.*;
import pl.szymanski.sharelibrary.requests.*;
import pl.szymanski.sharelibrary.utils.generator.*;

class RequestConverterTest {


    @Test
    void should_return_boo() {
        //given
        AddBookRequest addBookRequest = BookGenerator.getAddBookRequest();
        //when
        Book book = RequestConverter.addBookRequestToBook(addBookRequest);
        //then
        Assertions.assertThat(book).isNotNull();
        Assertions.assertThat(book.getAuthors()).isNotEmpty();
        Assertions.assertThat(book.getAuthors().size()).isEqualTo(addBookRequest.getAuthors().size());
        Assertions.assertThat(book.getCategories()).isNotEmpty();
        Assertions.assertThat(book.getCategories().size()).isEqualTo(addBookRequest.getCategories().size());
        Assertions.assertThat(book.getLanguage().getId()).isEqualTo(addBookRequest.getLanguage().getId());
        Assertions.assertThat(book.getCondition().ordinal()).isEqualTo((addBookRequest.getConditionId()));
    }

    @Test
    void should_return_category() {
        //given
        CategoryRequest request = CategoryGenerator.getCategoryRequest();
        //when
        Category category = RequestConverter.categoryRequestToCategory(request);
        //then
        Assertions.assertThat(category).isNotNull();
        Assertions.assertThat(category.getId()).isEqualTo(request.getId());
        Assertions.assertThat(category.getName()).isEqualTo(request.getName());

    }

    @Test
    void should_return_author() {
        //given
        AuthorRequest request = AuthorGenerator.getAuthorRequest();
        //when
        Author author = RequestConverter.authorRequestToAuthor(request);
        //then
        Assertions.assertThat(author).isNotNull();
        Assertions.assertThat(author.getName()).isEqualTo(request.getName());
        Assertions.assertThat(author.getSurname()).isEqualTo(request.getSurname());

    }

    @Test
    void should_return_coordinates() {
        //given
        CoordinatesRequest request = CoordinatesGenerator.getCoordinatesRequest();
        //when
        Coordinates category = RequestConverter.coordinatesRequestToCoordinates(request);
        //then
        Assertions.assertThat(category).isNotNull();
        Assertions.assertThat(category.getLatitude()).isEqualTo(request.getLatitude());
        Assertions.assertThat(category.getLongitude()).isEqualTo(request.getLongitude());
    }

    @Test
    void should_return_language() {
        //given
        LanguageRequest request = LanguageGenerator.getLanguageRequest();
        //when
        Language language = RequestConverter.languageRequestToLanguage(request);
        //then
        Assertions.assertThat(language).isNotNull();
        Assertions.assertThat(language.getId()).isEqualTo(request.getId());

    }

    @Test
    void should_return_user() {
        //given
        UserRequest request = UserGenerator.getUserRequest();
        //when
        User user = RequestConverter.userRequestToUser(request);
        //then
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getEmail()).isEqualTo(request.getEmail());
        Assertions.assertThat(user.getUsername()).isEqualTo(request.getUsername());
        Assertions.assertThat(user.getName()).isEqualTo(request.getName());
        Assertions.assertThat(user.getSurname()).isEqualTo(request.getSurname());
        Assertions.assertThat(user.getPassword()).isEqualTo(request.getPassword());


    }

    @Test
    void should_return_exchange() {
        //given
        AddExchangeRequest request = ExchangeGenerator.getAddExchangeRequest();
        //when
        Exchange exchange = RequestConverter.addExchangeRequestToExchange(request);
        //then
        Assertions.assertThat(exchange).isNotNull();
        Assertions.assertThat(exchange.getDeposit()).isEqualTo(request.getDeposit());

    }
}
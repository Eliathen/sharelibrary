package pl.szymanski.sharelibrary.services.adapters;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.szymanski.sharelibrary.converters.RequestConverter;
import pl.szymanski.sharelibrary.entity.*;
import pl.szymanski.sharelibrary.enums.BookCondition;
import pl.szymanski.sharelibrary.enums.BookStatus;
import pl.szymanski.sharelibrary.enums.ExchangeStatus;
import pl.szymanski.sharelibrary.exceptions.exchanges.ExchangeNotExist;
import pl.szymanski.sharelibrary.repositories.ports.CategoryRepository;
import pl.szymanski.sharelibrary.repositories.ports.CoordinatesRepository;
import pl.szymanski.sharelibrary.repositories.ports.ExchangeRepository;
import pl.szymanski.sharelibrary.repositories.ports.LanguageRepository;
import pl.szymanski.sharelibrary.requests.AddExchangeRequest;
import pl.szymanski.sharelibrary.services.ports.BookService;
import pl.szymanski.sharelibrary.services.ports.UserService;
import pl.szymanski.sharelibrary.utils.generator.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExchangeServiceImplTest {

    @Mock
    ExchangeRepository exchangeRepository;
    @Mock
    BookService bookService;
    @Mock
    UserService userService;
    @Mock
    CoordinatesRepository coordinatesRepository;
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    LanguageRepository languageRepository;

    @InjectMocks
    ExchangeServiceImpl exchangeService;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void cleanUp() {

    }

    @Test
    void shouldReturnZero() {
        //given
        double lat1 = 52.1968542;
        double lon1 = 20.9230059;
        double lat2 = 52.1968542;
        double lon2 = 20.9230059;
        //when
        double distance = exchangeService.countDistanceBetweenPoints(lat1, lon1, lat2, lon2);
        //then
        Assertions.assertThat(distance).isEqualTo(0.0);
    }

    @Test
    void shouldReturnCorrectValue() {
        //given
        double lat1 = 51.5;
        double lon1 = 0;
        double lat2 = 38.8;
        double lon2 = -77.1;
        //when
        double distance = exchangeService.countDistanceBetweenPoints(lat1, lon1, lat2, lon2);
        //then
        Assertions.assertThat(distance).isCloseTo(5918185.064088764, Percentage.withPercentage(0.001));
    }

    @Test
    void shouldReturnEmptyOfExchangeWithGivenLanguage() {
        //given
        List<Exchange> list = ExchangeGenerator.getExchangeList();
        Language language = LanguageGenerator.getLanguage();
        language.setId(5);
        language.setName("German");
        //when
        List<Exchange> result = exchangeService.filterByLanguage(list, language);
        //then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnListOfExchangeWithGivenLanguage() {
        //given
        List<Exchange> list = ExchangeGenerator.getExchangeList();
        Language language = LanguageGenerator.getLanguage();
        //when
        List<Exchange> result = exchangeService.filterByLanguage(list, language);
        //then
        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void shouldReturnListOfExchangeWithGivenCondition() {
        //given
        List<Exchange> list = ExchangeGenerator.getExchangeList();
        //when
        List<Exchange> result = exchangeService.filterByBookCondition(list, List.of(BookCondition.BAD));
        //then
        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void shouldReturnListOfExchangeWithOneElementByGivenTitle() {
        Exchange exchange1 = ExchangeGenerator.getExchange();
        Exchange exchange2 = ExchangeGenerator.getExchange();
        String other_title = "Other Title";
        exchange2.getBook().setTitle(other_title);
        List<Exchange> exchanges = new ArrayList<>(List.of(exchange1));
        exchanges.add(exchange2);
        Set<Exchange> result = exchangeService.filterByTitle(exchanges, "Other title");
        Assertions.assertThat(result).hasSize(1);
    }

    @Test
    void shouldReturnListOfExchangeWithOneElementByGivenAuthorSurname() {
        Exchange exchange1 = ExchangeGenerator.getExchange();
        Exchange exchange2 = ExchangeGenerator.getExchange();
        String other_title = "Other Title";
        exchange2.getBook().setTitle(other_title);
        exchange2.getBook().getAuthors().stream().findFirst().get().setName("John");
        exchange2.getBook().getAuthors().stream().findFirst().get().setSurname("Johnson");
        List<Exchange> exchanges = new ArrayList<>(List.of(exchange1));
        exchanges.add(exchange2);
        Set<Exchange> result = exchangeService.filterByAuthors(exchanges, "Tolkien");
        Assertions.assertThat(result).hasSize(1);
    }

    @Test
    void shouldReturnListOfExchangeWithOneElementByGivenQuery() {
        Exchange exchange1 = ExchangeGenerator.getExchange();
        Exchange exchange2 = ExchangeGenerator.getExchange();
        String other_title = "Other Title";
        exchange2.getBook().setTitle(other_title);
        exchange2.getBook().getAuthors().stream().findFirst().get().setName("John");
        exchange2.getBook().getAuthors().stream().findFirst().get().setSurname("Johnson");
        List<Exchange> exchanges = new ArrayList<>(List.of(exchange1));
        exchanges.add(exchange2);
        List<Exchange> result = exchangeService.filterByQuery(exchanges, "Tolkien");
        Assertions.assertThat(result).hasSize(1);
    }

    @Test
    void shouldReturnEmptyListWithGivenCondition() {
        //given
        List<Exchange> list = ExchangeGenerator.getExchangeList();
        //when
        List<Exchange> result = exchangeService.filterByBookCondition(list, List.of(BookCondition.NEW));
        //then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnListOfExchangeWithGivenCategory() {
        //given
        List<Exchange> list = ExchangeGenerator.getExchangeList();
        List<Category> categories = List.of(CategoryGenerator.getCategory());
        //when
        List<Exchange> result = exchangeService.filterByCategory(list, categories);
        //then
        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void shouldReturnEmptyListOfExchangeWithGivenCategory() {
        //given
        List<Exchange> list = ExchangeGenerator.getExchangeList();
        Category category = CategoryGenerator.getCategory();
        category.setId(3);
        category.setName("Biography");
        List<Category> categories = List.of(category);
        //when
        List<Exchange> result = exchangeService.filterByCategory(list, categories);
        //then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenLatitudeIsNull() {
        //given
        List<String> categoryList = List.of("Polish");
        //when
        Assertions.assertThatThrownBy(
                () -> exchangeService.filter(null,
                        50.12,
                        100.0,
                        categoryList,
                        "",
                        1,
                        List.of(1)
                )
        ).isInstanceOf(IllegalArgumentException.class);
        //then
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenLongitudeIsNull() {
        //given
        List<String> categoryList = List.of("Biography");
        //when
        Assertions.assertThatThrownBy(
                () -> exchangeService.filter(50.12,
                        null,
                        100.0,
                        categoryList,
                        "",
                        1,
                        List.of(1)
                )
        ).isInstanceOf(IllegalArgumentException.class);
        //then
    }

    @Test
    void shouldReturnListWithOneElement() {
        //given
        List<Exchange> exchangeList = List.of(ExchangeGenerator.getExchange());
        when(exchangeRepository.getAll()).thenReturn(exchangeList);
        //when
        List<Exchange> result = exchangeService.getExchangesByUserId(1L);
        //then
        Assertions.assertThat(result).hasSize(1);
    }

    @Test
    void shouldReturnExchangeById() {
        //given
        Exchange exchange = ExchangeGenerator.getExchange();
        when(exchangeRepository.getExchangeById(exchange.getId())).thenReturn(java.util.Optional.of(exchange));
        //when
        Exchange result = exchangeService.getExchangeById(1L);
        //then
        Assertions.assertThat(result.getId()).isEqualTo(exchange.getId());
    }

    @Test
    void shouldFinishExchange() {
        Exchange exchange = ExchangeGenerator.getExchange();
        Exchange newExchange = ExchangeGenerator.getExchange();
        when(exchangeRepository.getExchangeById(exchange.getId()))
                .thenReturn(Optional.of(exchange));
        newExchange.setExchangeStatus(ExchangeStatus.FINISHED);
        newExchange.getUser().getBooks().stream().findFirst().get().setAtUser(null);
        newExchange.getUser().getBooks().stream().findFirst().get().setStatus(BookStatus.AT_OWNER);
        newExchange.getWithUser().getBooks().stream().findFirst().get().setAtUser(null);
        newExchange.getWithUser().getBooks().stream().findFirst().get().setStatus(BookStatus.AT_OWNER);
        ArgumentCaptor<Exchange> argument = ArgumentCaptor.forClass(Exchange.class);
        when(exchangeRepository.saveExchange(any())).thenReturn(newExchange);

        exchangeService.finishExchange(exchange.getId());

        verify(exchangeRepository).saveExchange(argument.capture());
        Assertions.assertThat(argument.getValue().getId()).isEqualTo(exchange.getId());
        Assertions.assertThat(argument.getValue().getExchangeStatus()).isEqualTo(ExchangeStatus.FINISHED);


    }

    @Test
    void shouldThrowExceptionExchangeNotExists() {
        Exchange exchange = ExchangeGenerator.getExchange();
        when(exchangeRepository.getExchangeById(exchange.getId()))
                .thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() ->
                exchangeService.getExchangeById(exchange.getId())
        ).isInstanceOf(ExchangeNotExist.class);
    }

    @Test
    void shouldSaveExchange() {
        User user = UserGenerator.getUser();
        user.setBooks(List.of(BookGenerator.getUserBook()));
        Book book = user.getBooks().stream().findFirst().get().getBook();
        AddExchangeRequest request = ExchangeGenerator.getAddExchangeRequest();
        Coordinates coordinates = CoordinatesGenerator.getCoordinates();
        when(coordinatesRepository.findByLatitudeAndLongitude(any(), any())).thenReturn(Optional.of(coordinates));
        when(bookService.findBookById(request.getBookId())).thenReturn(book);
        when(userService.getUserById(request.getUserId())).thenReturn(user);

        ArgumentCaptor<Exchange> argument = ArgumentCaptor.forClass(Exchange.class);

        exchangeService.saveExchange(request);

        verify(exchangeRepository).saveExchange(argument.capture());

        Assertions.assertThat(argument.getValue().getDeposit()).isEqualTo(request.getDeposit());
        Assertions.assertThat(argument.getValue().getBook().getId()).isEqualTo(request.getBookId());
        Assertions.assertThat(argument.getValue().getUser().getId()).isEqualTo(request.getUserId());
        Assertions.assertThat(argument.getValue().getCoordinates().getLatitude())
                .isEqualTo(RequestConverter.coordinatesRequestToCoordinates(request.getCoordinates()).getLatitude());
        Assertions.assertThat(argument.getValue().getCoordinates().getLongitude())
                .isEqualTo(RequestConverter.coordinatesRequestToCoordinates(request.getCoordinates()).getLongitude());
    }

    @Test
    void shouldReturnListOfExchangeByGivenWithUserId() {
        Exchange first = ExchangeGenerator.getExchange();
        Exchange second = ExchangeGenerator.getExchange();
        second.getWithUser().setId(2L);
        List<Exchange> exchanges = List.of(first, second);
        when(exchangeRepository.getExchangeByStatus(any())).thenReturn(exchanges);

        List<Exchange> result = exchangeService.getExchangesByWithUserId(1L);
        Assertions.assertThat(result).hasSize(1);
    }

    @Test
    void shouldReturnExchangesInRadius() {
        ArgumentCaptor<Double> firstCaptor = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Double> secondCaptor = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Double> thirdCaptor = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Double> forthCaptor = ArgumentCaptor.forClass(Double.class);
        double lat = 40.689200;//0.7102 rad
        double lon = -74.044406;//-1.2923 rad
        double radius = 100.0;
        exchangeService.getExchangesByCoordinatesAndRadius(lat, lon, radius);
        verify(exchangeRepository).getExchangeByBoundingCoordinates(firstCaptor.capture(), secondCaptor.capture(), thirdCaptor.capture(), forthCaptor.capture());
        Assertions.assertThat(firstCaptor.getValue()).isCloseTo(39.78987856923, Percentage.withPercentage(0.5));//1.2393 rad
        Assertions.assertThat(secondCaptor.getValue()).isCloseTo(41.58852143069, Percentage.withPercentage(0.5));//1.5532 rad
        Assertions.assertThat(thirdCaptor.getValue()).isCloseTo(-74.943790549, Percentage.withPercentage(0.5));// -1.8184 rad
        Assertions.assertThat(forthCaptor.getValue()).isCloseTo(-73.145009490, Percentage.withPercentage(0.5));//0.4221 rad

    }
}
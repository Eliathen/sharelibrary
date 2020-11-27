package pl.szymanski.sharelibrary.services.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szymanski.sharelibrary.commanddata.AddExchangeRequest;
import pl.szymanski.sharelibrary.commanddata.CoordinatesRequest;
import pl.szymanski.sharelibrary.converters.RequestConverter;
import pl.szymanski.sharelibrary.entity.Book;
import pl.szymanski.sharelibrary.entity.Coordinates;
import pl.szymanski.sharelibrary.entity.Exchange;
import pl.szymanski.sharelibrary.entity.User;
import pl.szymanski.sharelibrary.enums.ExchangeStatus;
import pl.szymanski.sharelibrary.exceptions.books.BookDoesNotExist;
import pl.szymanski.sharelibrary.exceptions.exchanges.ExchangeNotExists;
import pl.szymanski.sharelibrary.repositories.ports.BookRepository;
import pl.szymanski.sharelibrary.repositories.ports.CoordinatesRepository;
import pl.szymanski.sharelibrary.repositories.ports.ExchangeRepository;
import pl.szymanski.sharelibrary.services.ports.ExchangeService;
import pl.szymanski.sharelibrary.services.ports.UserService;
import pl.szymanski.sharelibrary.views.ExchangeResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    private final ExchangeRepository exchangeRepository;
    private final BookRepository bookRepository;
    private final UserService userService;
    private final CoordinatesRepository coordinatesRepository;

    @Override
    public ExchangeResponse saveExchange(AddExchangeRequest addExchangeRequest) {
        Exchange exchange = RequestConverter.addExchangeRequestToExchange(addExchangeRequest);
        exchange.setBook(checkIfBookExists(addExchangeRequest.getBookId()));
        exchange.setUser(checkIfUserExists(addExchangeRequest.getUserId()));
        exchange.setCoordinates(checkIfCoordinatesExist(exchange.getCoordinates()));
        exchange.setExchangeStatus(ExchangeStatus.STARTED);
        return ExchangeResponse.of(exchangeRepository.saveExchange(exchange));
    }

    private Book checkIfBookExists(Long bookId) {
        return bookRepository.getBookById(bookId).orElseThrow(() ->
                new BookDoesNotExist(bookId)
        );
    }

    private User checkIfUserExists(Long userId) {
        return userService.getUserById(userId);
    }

    private Coordinates checkIfCoordinatesExist(Coordinates coordinates) {
        return coordinatesRepository.findByLatitudeAndLongitude(coordinates.getLatitude(), coordinates.getLongitude()).orElseGet(() ->
                coordinatesRepository.saveCoordinates(coordinates)
        );
    }

    @Override
    public void finishExchange(Long exchangeId) {
        Exchange exchange = exchangeRepository.getExchangeById(exchangeId).orElseThrow(() -> new ExchangeNotExists(exchangeId));
        exchange.setExchangeStatus(ExchangeStatus.FINISHED);
        ExchangeResponse.of(exchangeRepository.saveExchange(exchange));
    }

    @Override
    public List<ExchangeResponse> getStartedExchanges() {
        return exchangeRepository.getExchangeByStatus(ExchangeStatus.STARTED).stream().map(ExchangeResponse::of).collect(Collectors.toList());
    }

    @Override
    public List<ExchangeResponse> getExchangesByCoordinatesAndRadius(CoordinatesRequest coordinatesRequest, Double radius) {
        return null;
    }
}

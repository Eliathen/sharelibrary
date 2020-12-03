package pl.szymanski.sharelibrary.services.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.szymanski.sharelibrary.commanddata.AddExchangeRequest;
import pl.szymanski.sharelibrary.commanddata.CoordinatesRequest;
import pl.szymanski.sharelibrary.converters.RequestConverter;
import pl.szymanski.sharelibrary.entity.Coordinates;
import pl.szymanski.sharelibrary.entity.Exchange;
import pl.szymanski.sharelibrary.entity.User;
import pl.szymanski.sharelibrary.entity.UserBook;
import pl.szymanski.sharelibrary.enums.BookStatus;
import pl.szymanski.sharelibrary.enums.ExchangeStatus;
import pl.szymanski.sharelibrary.exceptions.exchanges.ExchangeNotExists;
import pl.szymanski.sharelibrary.repositories.ports.CoordinatesRepository;
import pl.szymanski.sharelibrary.repositories.ports.ExchangeRepository;
import pl.szymanski.sharelibrary.repositories.ports.UserRepository;
import pl.szymanski.sharelibrary.services.ports.BookService;
import pl.szymanski.sharelibrary.services.ports.ExchangeService;
import pl.szymanski.sharelibrary.services.ports.UserService;
import pl.szymanski.sharelibrary.views.ExchangeResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    private final ExchangeRepository exchangeRepository;
    private final BookService bookService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final CoordinatesRepository coordinatesRepository;

    @Override
    public ExchangeResponse saveExchange(AddExchangeRequest addExchangeRequest) {
        Exchange exchange = RequestConverter.addExchangeRequestToExchange(addExchangeRequest);
        exchange.setBook(bookService.findBookById(addExchangeRequest.getBookId()));
        exchange.setUser(userService.getUserById(addExchangeRequest.getUserId()));
        exchange.setCoordinates(checkIfCoordinatesExist(exchange.getCoordinates()));
        exchange.setExchangeStatus(ExchangeStatus.STARTED);
        exchange.getUser().getBooks().forEach(
                it -> {
                    if (it.getBook().getId().equals(exchange.getBook().getId())) {
                        it.setStatus(BookStatus.DURING_EXCHANGE);
                    }
                }
        );
        return ExchangeResponse.of(exchangeRepository.saveExchange(exchange));
    }

    private Coordinates checkIfCoordinatesExist(Coordinates coordinates) {
        return coordinatesRepository.findByLatitudeAndLongitude(coordinates.getLatitude(), coordinates.getLongitude()).orElseGet(() ->
                coordinatesRepository.saveCoordinates(coordinates)
        );
    }

    @Override
    @Transactional
    public void finishExchange(Long exchangeId) {
        Exchange exchange = exchangeRepository.getExchangeById(exchangeId).orElseThrow(() -> new ExchangeNotExists(exchangeId));
        exchange.setExchangeStatus(ExchangeStatus.FINISHED);
        Long id = exchangeRepository.saveExchange(exchange).getBook().getId();
        User user = userService.getUserById(exchange.getUser().getId());
        List<UserBook> userBooks = user.getBooks();
        userBooks.forEach(ub -> {
            if (ub.getBook().getId().equals(id)) {
                ub.setStatus(BookStatus.AT_OWNER);
            }
        });
        user.setBooks(userBooks);
        userRepository.saveUser(user);
    }

    @Override
    public List<ExchangeResponse> getStartedExchanges() {
        return exchangeRepository.getExchangeByStatus(
                ExchangeStatus.STARTED
        ).stream().map(ExchangeResponse::of).collect(Collectors.toList());
    }

    @Override
    public List<ExchangeResponse> getExchangesByCoordinatesAndRadius(CoordinatesRequest coordinatesRequest, Double radius) {
        return null;
    }
}

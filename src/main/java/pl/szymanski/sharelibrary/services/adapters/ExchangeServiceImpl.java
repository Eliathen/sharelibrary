package pl.szymanski.sharelibrary.services.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import pl.szymanski.sharelibrary.requests.AddExchangeRequest;
import pl.szymanski.sharelibrary.requests.CoordinatesRequest;
import pl.szymanski.sharelibrary.requests.ExecuteExchangeRequest;
import pl.szymanski.sharelibrary.services.ports.BookService;
import pl.szymanski.sharelibrary.services.ports.ExchangeService;
import pl.szymanski.sharelibrary.services.ports.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    private final ExchangeRepository exchangeRepository;
    private final BookService bookService;
    private final UserService userService;
    private final CoordinatesRepository coordinatesRepository;

    @Override
    public Exchange saveExchange(AddExchangeRequest addExchangeRequest) {
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
        return exchangeRepository.saveExchange(exchange);
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
        exchangeRepository.saveExchange(exchange);
//        User user = userService.getUserById(exchange.getUser().getId());
        changeBookStatus(exchange.getUser().getId(), exchange.getBook().getId(), BookStatus.AT_OWNER);
//        List<UserBook> userBooks = user.getBooks();
//        userBooks.forEach(ub -> {
//            if (ub.getBook().getId().equals(id)) {
//                ub.setStatus(BookStatus.AT_OWNER);
//            }
//        });
//        user.setBooks(userBooks);
//        userRepository.saveUser(user);
    }

    @Override
    public List<Exchange> getStartedExchanges() {
        return exchangeRepository.getExchangeByStatus(
                ExchangeStatus.STARTED
        );
    }

    @Override
    public Exchange getExchangeById(Long id) {
        return exchangeRepository.getExchangeById(id).orElseThrow(() -> new ExchangeNotExists(id));
    }

    @Override
    public List<Exchange> getExchangesByCoordinatesAndRadius(CoordinatesRequest coordinatesRequest, Double radius) {
        return null;
    }

    @Override
    @Transactional
    public Exchange executeExchange(ExecuteExchangeRequest executeExchangeRequest) {
        Exchange exchange = getExchangeById(executeExchangeRequest.getExchangeId());
        User withUser = userService.getUserById(executeExchangeRequest.getWithUserId());
        if (executeExchangeRequest.getForBookId() != null) {
            exchange.setForBook(bookService.findBookById(executeExchangeRequest.getForBookId()));
            withUser = changeBookStatusAndAtUser(executeExchangeRequest.getWithUserId(), exchange.getForBook().getId(), BookStatus.EXCHANGED, exchange.getUser().getId());
        }
        User owner = changeBookStatusAndAtUser(exchange.getUser().getId(), exchange.getBook().getId(), BookStatus.EXCHANGED, executeExchangeRequest.getWithUserId());
        exchange.setExchangeStatus(ExchangeStatus.DURING);
        exchange.setWithUser(withUser);
        exchange.setUser(owner);
        return exchangeRepository.saveExchange(exchange);
    }

    private User changeBookStatus(Long userId, Long bookId, BookStatus newStatus) {
        User user = userService.getUserById(userId);
        List<UserBook> userBooks = user.getBooks();
        userBooks.forEach(ub -> {
            if (ub.getBook().getId().equals(bookId)) {
                ub.setStatus(newStatus);
            }
        });
        user.setBooks(userBooks);
        return user;
    }

    private User changeBookStatusAndAtUser(Long userId, Long bookId, BookStatus newStatus, Long atUserId) {
        User user = userService.getUserById(userId);
        User atUser = userService.getUserById(atUserId);
        List<UserBook> userBooks = user.getBooks();
        userBooks.forEach(ub -> {
            if (ub.getBook().getId().equals(bookId)) {
                ub.setStatus(newStatus);
                ub.setAtUser(atUser);
            }
        });
        user.setBooks(userBooks);
        return user;
    }

}

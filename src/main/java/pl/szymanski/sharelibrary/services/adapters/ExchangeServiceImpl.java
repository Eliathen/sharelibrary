package pl.szymanski.sharelibrary.services.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.szymanski.sharelibrary.converters.RequestConverter;
import pl.szymanski.sharelibrary.entity.*;
import pl.szymanski.sharelibrary.enums.BookCondition;
import pl.szymanski.sharelibrary.enums.BookStatus;
import pl.szymanski.sharelibrary.enums.ExchangeStatus;
import pl.szymanski.sharelibrary.exceptions.exchanges.ExchangeNotExists;
import pl.szymanski.sharelibrary.repositories.ports.CategoryRepository;
import pl.szymanski.sharelibrary.repositories.ports.CoordinatesRepository;
import pl.szymanski.sharelibrary.repositories.ports.ExchangeRepository;
import pl.szymanski.sharelibrary.repositories.ports.LanguageRepository;
import pl.szymanski.sharelibrary.requests.AddExchangeRequest;
import pl.szymanski.sharelibrary.requests.CoordinatesRequest;
import pl.szymanski.sharelibrary.requests.ExecuteExchangeRequest;
import pl.szymanski.sharelibrary.response.ExchangeResponse;
import pl.szymanski.sharelibrary.services.ports.BookService;
import pl.szymanski.sharelibrary.services.ports.ExchangeService;
import pl.szymanski.sharelibrary.services.ports.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    private final ExchangeRepository exchangeRepository;
    private final BookService bookService;
    private final UserService userService;
    private final CoordinatesRepository coordinatesRepository;
    private final CategoryRepository categoryRepository;
    private final LanguageRepository languageRepository;

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
                        it.setStatus(BookStatus.SHARED);
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
        changeUserBookStatus(exchange.getUser().getId(), exchange.getBook().getId(), BookStatus.AT_OWNER);
        if (exchange.getForBook() != null) {
            changeUserBookStatus(exchange.getWithUser().getId(), exchange.getForBook().getId(), BookStatus.AT_OWNER);
        }
    }

    @Override
    public List<Exchange> getExchangesByUserId(Long userId) {
        return exchangeRepository.getAll().stream().filter(it -> it.getExchangeStatus() != ExchangeStatus.FINISHED && it.getUser().getId().equals(userId)).collect(Collectors.toList());
    }

    @Override
    public Exchange getExchangeById(Long id) {
        return exchangeRepository.getExchangeById(id).orElseThrow(() -> new ExchangeNotExists(id));
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
        exchange.getRequirements().forEach(requirement -> requirement.setActual(false));

        Exchange savedExchange = exchangeRepository.saveExchange(exchange);
        //If for book is shared. Finish exchange
        if (savedExchange != null && savedExchange.getForBook() != null) {
            getUserExchange(withUser.getId())
                    .stream()
                    .filter(it -> it.getExchangeStatus().equals(ExchangeStatus.STARTED) && it.getBook().getId().equals(savedExchange.getForBook().getId()))
                    .findFirst().ifPresent(it -> {
                it.setExchangeStatus(ExchangeStatus.DURING);
                exchangeRepository.saveExchange(it);
            });
        }
        return savedExchange;
    }

    private List<Exchange> getUserExchange(Long userId) {
        return exchangeRepository.getAll().stream().filter(it -> it.getUser().getId().equals(userId)).collect(Collectors.toList());
    }

    @Override
    public List<Requirement> getRequirements(Long exchangeId) {
        return getExchangeById(exchangeId).getRequirements().stream().filter(Requirement::isActual).collect(Collectors.toList());
    }

    private User changeUserBookStatus(Long userId, Long bookId, BookStatus newStatus) {
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

    public List<Exchange> getExchangesByWithUserId(Long userId) {
        System.out.println();
        List<Exchange> exchanges = exchangeRepository.getExchangeByStatus(ExchangeStatus.DURING);
        return exchanges.stream()
                .filter(it -> it.getWithUser() != null && it.getWithUser().getId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<ExchangeResponse> filter(Double latitude,
                                         Double longitude,
                                         Double radius,
                                         List<String> categories,
                                         String query,
                                         Integer languageId,
                                         Integer bookCondition) {
        List<Exchange> exchanges = filterByCoordinatesAndRadius(latitude, longitude, radius)
                .stream()
                .filter(it -> it.getExchangeStatus().equals(ExchangeStatus.STARTED))
                .collect(Collectors.toList());
        if (bookCondition != null) {
            exchanges = filterByBookCondition(exchanges, BookCondition.values()[bookCondition]);
        }
        if (languageId != null) {
            Optional<Language> language = languageRepository.getLanguageById(languageId);
            if (language.isPresent()) {
                exchanges = filterByLanguage(exchanges, language.get());
            }
        }
        if (categories != null) {
            List<Category> newCategories = getCategoryListFromNameList(categories);
            exchanges = filterByCategory(exchanges, newCategories);
        }
        if (query != null && !query.isBlank()) {
            exchanges = filterByQuery(exchanges, query);
        }
        return exchanges.stream().map(it -> ExchangeResponse.of(it,
                countDistanceBetweenPoints(
                        latitude,
                        longitude,
                        it.getCoordinates().getLatitude(),
                        it.getCoordinates().getLongitude())))
                .collect(Collectors.toList());
    }

    private List<Exchange> filterByLanguage(List<Exchange> exchanges, Language language) {
        return exchanges.stream()
                .filter(it -> it.getBook().getLanguage().getId().equals(language.getId()))
                .collect(Collectors.toList());
    }

    private List<Exchange> filterByBookCondition(List<Exchange> exchanges, BookCondition bookCondition) {
        return exchanges.stream()
                .filter(it -> it.getBook().getCondition().equals(bookCondition))
                .collect(Collectors.toList());
    }

//
//    private List<Exchange> filterByCategoryAndQuery(List<Exchange> exchanges, List<Category> categories, String query) {
//        Set<Exchange> result = new HashSet<>(filterByCategory(exchanges, categories));
//        return new LinkedList<>(filterByQuery(new ArrayList<>(result), query));
//    }

    private List<Exchange> filterByQuery(List<Exchange> exchanges, String query) {
        List<String> queries = Arrays.asList(query.split(" "));
        Set<Exchange> result = filterByTitle(exchanges, query);
        result.addAll(filterByAuthors(exchanges, query));
        queries.forEach(q -> {
            result.addAll(filterByTitle(exchanges, q));
            result.addAll(filterByAuthors(exchanges, q));
        });
        return new LinkedList<>(result);
    }

    private Set<Exchange> filterByTitle(List<Exchange> exchanges, String query) {
        Set<Exchange> result = new HashSet<>();
        exchanges.forEach(it -> {
            if (it.getBook().getTitle().toLowerCase().contains(query)) {
                result.add(it);
            }
        });
        return result;
    }

    private Set<Exchange> filterByAuthors(List<Exchange> exchanges, String query) {
        Set<Exchange> result = new HashSet<>();
        exchanges.forEach(exchange -> {
            if (exchange.getBook().getAuthors()
                    .stream()
                    .anyMatch(author -> author.getName().contains(query) || author.getSurname().contains(query))) {
                result.add(exchange);
            }
        });
        return result;
    }

    @Override
    public double countDistanceBetweenPoints(double lat1, Double lon1, double lat2, Double lon2) {
        double radiusInM = 6371000.0;
        double thetaLat = Math.toRadians(lat2 - lat1);
        double thetaLong = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        double dist =
                Math.sin(thetaLat / 2) * Math.sin(thetaLat / 2) +
                        Math.cos(lat1) * Math.cos(lat2) *
                                Math.sin(thetaLong / 2) * Math.sin(thetaLong / 2);
        return 2 * Math.atan2(Math.sqrt(dist), Math.sqrt(1 - dist)) * radiusInM;
    }

    private List<Exchange> filterByCoordinatesAndRadius(Double latitude, Double longitude, Double radius) {
        return exchangeRepository.getExchangeByCoordinatesAndRadius(latitude, longitude, radius);
    }

    private LinkedList<Exchange> filterByCategory(List<Exchange> exchanges, List<Category> categories) {
        exchanges = exchanges.stream()
                .filter(exchange -> exchange.getExchangeStatus() == ExchangeStatus.STARTED)
                .collect(Collectors.toList());
        List<Exchange> finalExchanges = getExchangeWhichContainsAllCategories(exchanges, categories);
        return new LinkedList<>(finalExchanges);
    }

    private List<Exchange> getExchangeWhichContainsAllCategories(List<Exchange> exchanges, List<Category> categories) {
        return exchanges.stream().filter(
                exchange -> exchange.getBook().getCategories().containsAll(categories)
        ).collect(Collectors.toList());
    }


    private List<Category> getCategoryListFromNameList(List<String> categories) {
        List<Category> categoryList = new LinkedList<>();
        categories.forEach(it ->
                categoryRepository.findByName(it).ifPresent(categoryList::add)
        );
        return categoryList;
    }

    @Override
    public List<Exchange> getExchangesByCoordinatesAndRadius(CoordinatesRequest coordinatesRequest, Double radius) {
        Double latitude = coordinatesRequest.getLatitude();
        Double longitude = coordinatesRequest.getLongitude();

        return exchangeRepository.getExchangeByCoordinatesAndRadius(
                latitude,
                longitude,
                radius
        );
    }

}

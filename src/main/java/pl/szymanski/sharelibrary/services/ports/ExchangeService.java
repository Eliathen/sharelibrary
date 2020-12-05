package pl.szymanski.sharelibrary.services.ports;

import pl.szymanski.sharelibrary.entity.Exchange;
import pl.szymanski.sharelibrary.requests.AddExchangeRequest;
import pl.szymanski.sharelibrary.requests.CoordinatesRequest;
import pl.szymanski.sharelibrary.requests.ExecuteExchangeRequest;

import java.util.List;

public interface ExchangeService {

    Exchange saveExchange(AddExchangeRequest addExchangeRequest);

    void finishExchange(Long exchangeId);

    List<Exchange> getStartedExchanges();

    Exchange getExchangeById(Long id);

    List<Exchange> getExchangesByCoordinatesAndRadius(CoordinatesRequest coordinates, Double radius);

    Exchange executeExchange(ExecuteExchangeRequest executeExchangeRequest);

}

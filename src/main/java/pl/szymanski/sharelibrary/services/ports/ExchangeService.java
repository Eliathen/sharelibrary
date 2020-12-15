package pl.szymanski.sharelibrary.services.ports;

import pl.szymanski.sharelibrary.entity.Exchange;
import pl.szymanski.sharelibrary.entity.Requirement;
import pl.szymanski.sharelibrary.requests.AddExchangeRequest;
import pl.szymanski.sharelibrary.requests.CoordinatesRequest;
import pl.szymanski.sharelibrary.requests.ExecuteExchangeRequest;
import pl.szymanski.sharelibrary.response.ExchangeResponse;

import java.util.List;

public interface ExchangeService {

    Exchange saveExchange(AddExchangeRequest addExchangeRequest);

    void finishExchange(Long exchangeId);

    List<Exchange> getExchanges();

    Exchange getExchangeById(Long id);

    List<Exchange> getExchangesByCoordinatesAndRadius(CoordinatesRequest coordinates, Double radius);

    Exchange executeExchange(ExecuteExchangeRequest executeExchangeRequest);

    List<Requirement> getRequirements(Long exchangeId);

    List<Exchange> getExchangesWhereUserIdIsWithUser(Long userId);

    List<ExchangeResponse> filter(Double latitude,
                                  Double longitude,
                                  Double radius,
                                  List<String> categories,
                                  String query
    );
}

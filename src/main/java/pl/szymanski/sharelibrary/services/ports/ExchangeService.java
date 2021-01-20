package pl.szymanski.sharelibrary.services.ports;

import pl.szymanski.sharelibrary.entity.Exchange;
import pl.szymanski.sharelibrary.entity.Requirement;
import pl.szymanski.sharelibrary.requests.AddExchangeRequest;
import pl.szymanski.sharelibrary.requests.ExecuteExchangeRequest;
import pl.szymanski.sharelibrary.response.ExchangeResponse;

import java.util.List;

public interface ExchangeService {

    Exchange saveExchange(AddExchangeRequest addExchangeRequest);

    void finishExchange(Long exchangeId);

    List<Exchange> getExchangesByUserId(Long userId);

    Exchange getExchangeById(Long id);

    List<Exchange> getExchangesByCoordinatesAndRadius(double latitude, double longitude, double radius);

    Exchange executeExchange(ExecuteExchangeRequest executeExchangeRequest);

    List<Requirement> getRequirements(Long exchangeId);

    List<Exchange> getExchangesByWithUserId(Long userId);

    List<ExchangeResponse> filter(Double latitude,
                                  Double longitude,
                                  Double radius,
                                  List<String> categories,
                                  String query,
                                  Integer languageId,
                                  List<Integer> conditions
    );

    double countDistanceBetweenPoints(double lat1, double lon1, double lat2, double lon2);

    List<Exchange> getExchangesLinkedByUser(Long userId);
}

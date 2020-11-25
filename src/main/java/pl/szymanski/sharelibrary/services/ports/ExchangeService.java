package pl.szymanski.sharelibrary.services.ports;

import pl.szymanski.sharelibrary.commanddata.AddExchangeRequest;
import pl.szymanski.sharelibrary.commanddata.CoordinatesRequest;
import pl.szymanski.sharelibrary.views.ExchangeResponse;

import java.util.List;

public interface ExchangeService {

    ExchangeResponse saveExchange(AddExchangeRequest addExchangeRequest);

    void finishExchange(Long exchangeId);

    List<ExchangeResponse> getNotFinishedExchanges();

    List<ExchangeResponse> getExchangesByCoordinatesAndRadius(CoordinatesRequest coordinates, Double radius);

}

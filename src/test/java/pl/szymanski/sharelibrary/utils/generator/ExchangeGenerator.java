package pl.szymanski.sharelibrary.utils.generator;

import pl.szymanski.sharelibrary.entity.Exchange;
import pl.szymanski.sharelibrary.enums.ExchangeStatus;
import pl.szymanski.sharelibrary.response.ExchangeResponse;

public class ExchangeGenerator {
    public static Exchange getExchange() {
        Exchange exchange = new Exchange();

        exchange.setExchangeStatus(ExchangeStatus.STARTED);
        exchange.setUser(UserGenerator.getUserWithBooks());
        exchange.setBook(BookGenerator.getBook());
        exchange.setDeposit(200.0);
        exchange.setCoordinates(CoordinatesGenerator.getCoordinates());
        exchange.setId(1L);
        exchange.setForBook(BookGenerator.getBook());
        exchange.setWithUser(UserGenerator.getUser());
        return exchange;
    }

    public static ExchangeResponse getExchangeResponse() {
        return ExchangeResponse.of(getExchange());
    }
}

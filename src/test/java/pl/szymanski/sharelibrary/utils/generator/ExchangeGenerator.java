package pl.szymanski.sharelibrary.utils.generator;

import pl.szymanski.sharelibrary.entity.Exchange;
import pl.szymanski.sharelibrary.enums.ExchangeStatus;
import pl.szymanski.sharelibrary.requests.AddExchangeRequest;
import pl.szymanski.sharelibrary.requests.ExecuteExchangeRequest;
import pl.szymanski.sharelibrary.response.ExchangeResponse;

import static pl.szymanski.sharelibrary.utils.constant.ExchangeConstant.*;

public class ExchangeGenerator {
    public static Exchange getExchange() {
        Exchange exchange = new Exchange();

        exchange.setExchangeStatus(ExchangeStatus.STARTED);
        exchange.setUser(UserGenerator.getUserWithBooks());
        exchange.setBook(BookGenerator.getBook());
        exchange.setDeposit(TEST_EXCHANGE_DEPOSIT);
        exchange.setCoordinates(CoordinatesGenerator.getCoordinates());
        exchange.setId(TEST_EXCHANGE_ID);
        exchange.setForBook(BookGenerator.getBook());
        exchange.setWithUser(UserGenerator.getUser());
        return exchange;
    }

    public static ExchangeResponse getExchangeResponse() {
        return ExchangeResponse.of(getExchange());
    }

    public static AddExchangeRequest getAddExchangeRequest() {
        return new AddExchangeRequest(
                TEST_EXCHANGE_DEPOSIT,
                TEST_EXCHANGE_BOOK_ID,
                TEST_EXCHANGE_USER_ID,
                CoordinatesGenerator.getCoordinatesRequest()
        );
    }

    public static ExecuteExchangeRequest getExecuteExchangeRequest() {
        return new ExecuteExchangeRequest(
                TEST_EXCHANGE_ID,
                TEST_EXCHANGE_USER_ID,
                TEST_EXCHANGE_BOOK_ID
        );
    }
}

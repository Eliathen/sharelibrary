package pl.szymanski.sharelibrary.exceptions.exchanges;

import pl.szymanski.sharelibrary.exceptions.ExceptionMessages;

public class ExchangeNotExists extends RuntimeException {
    public ExchangeNotExists(Long exchangeId) {
        super(String.format(ExceptionMessages.EXCHANGE_DOES_NOT_EXIST, exchangeId));
    }
}

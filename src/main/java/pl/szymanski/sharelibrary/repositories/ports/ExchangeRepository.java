package pl.szymanski.sharelibrary.repositories.ports;

import pl.szymanski.sharelibrary.entity.Exchange;
import pl.szymanski.sharelibrary.enums.ExchangeStatus;

import java.util.List;
import java.util.Optional;

public interface ExchangeRepository {

    Exchange saveExchange(Exchange exchange);

    List<Exchange> getAll();

    Optional<Exchange> getExchangeById(Long id);

    List<Exchange> getExchangeByStatus(ExchangeStatus exchangeStatus);

}

package pl.szymanski.sharelibrary.repositories.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.szymanski.sharelibrary.entity.Exchange;
import pl.szymanski.sharelibrary.repositories.jpa.ExchangeJPARepository;
import pl.szymanski.sharelibrary.repositories.ports.ExchangeRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ExchangeRepositoryImpl implements ExchangeRepository {

    private final ExchangeJPARepository exchangeJPARepository;

    @Override
    public Exchange saveExchange(Exchange exchange) {
        return exchangeJPARepository.saveAndFlush(exchange);
    }


    @Override
    public List<Exchange> getAll() {
        return exchangeJPARepository.findAll();
    }

    @Override
    public Optional<Exchange> getExchangeById(Long id) {
        return exchangeJPARepository.findById(id);
    }

    @Override
    public List<Exchange> getNotFinishedExchanges(boolean isFinished) {
        return exchangeJPARepository.findAllByIsFinished(isFinished);
    }
}

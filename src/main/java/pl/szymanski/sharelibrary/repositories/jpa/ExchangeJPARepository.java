package pl.szymanski.sharelibrary.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szymanski.sharelibrary.entity.Exchange;
import pl.szymanski.sharelibrary.entity.User;
import pl.szymanski.sharelibrary.enums.ExchangeStatus;

import java.util.List;

public interface ExchangeJPARepository extends JpaRepository<Exchange, Long> {

    List<Exchange> findAllByExchangeStatus(ExchangeStatus exchangeStatus);

    List<Exchange> findAllByExchangeStatusAndUserIsNot(ExchangeStatus exchangeStatus, User user);
}

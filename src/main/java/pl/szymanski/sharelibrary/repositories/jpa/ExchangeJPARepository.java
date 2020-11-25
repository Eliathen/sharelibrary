package pl.szymanski.sharelibrary.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szymanski.sharelibrary.entity.Exchange;

import java.util.List;

public interface ExchangeJPARepository extends JpaRepository<Exchange, Long> {

    List<Exchange> findAllByIsFinished(boolean isFinished);
}

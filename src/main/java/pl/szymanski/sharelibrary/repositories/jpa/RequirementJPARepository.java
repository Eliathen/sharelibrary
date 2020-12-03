package pl.szymanski.sharelibrary.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szymanski.sharelibrary.entity.Requirement;

import java.util.List;

public interface RequirementJPARepository extends JpaRepository<Requirement, Long> {

    List<Requirement> getAllByExchangeId(Long exchangeId);

}

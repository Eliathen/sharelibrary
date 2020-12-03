package pl.szymanski.sharelibrary.repositories.ports;

import pl.szymanski.sharelibrary.entity.Requirement;

import java.util.List;
import java.util.Optional;

public interface RequirementRepository {

    Requirement saveRequirement(Requirement requirement);

    Optional<Requirement> getRequirementById(Long id);

    List<Requirement> getRequirementByExchange(Long exchangeId);

    List<Requirement> getRequirements();

}

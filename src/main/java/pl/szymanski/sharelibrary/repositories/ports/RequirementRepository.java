package pl.szymanski.sharelibrary.repositories.ports;

import pl.szymanski.sharelibrary.entity.Requirement;

import java.util.List;

public interface RequirementRepository {

    Requirement saveRequirement(Requirement requirement);

    List<Requirement> getRequirementByExchange(Long exchangeId);

    List<Requirement> getRequirements();

}

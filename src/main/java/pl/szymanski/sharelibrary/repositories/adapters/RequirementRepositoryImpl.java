package pl.szymanski.sharelibrary.repositories.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.szymanski.sharelibrary.entity.Requirement;
import pl.szymanski.sharelibrary.repositories.jpa.RequirementJPARepository;
import pl.szymanski.sharelibrary.repositories.ports.RequirementRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RequirementRepositoryImpl implements RequirementRepository {

    private final RequirementJPARepository requirementJPARepository;

    @Override
    public Requirement saveRequirement(Requirement requirement) {
        return requirementJPARepository.saveAndFlush(requirement);
    }

    @Override
    public Optional<Requirement> getRequirementById(Long id) {
        return requirementJPARepository.findById(id);
    }

    @Override
    public List<Requirement> getRequirementByExchange(Long exchangeId) {
        return requirementJPARepository.getAllByExchangeId(exchangeId);
    }

    @Override
    public List<Requirement> getRequirements() {
        return requirementJPARepository.findAll();
    }
}

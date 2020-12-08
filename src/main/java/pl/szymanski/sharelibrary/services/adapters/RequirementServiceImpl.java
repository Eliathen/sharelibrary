package pl.szymanski.sharelibrary.services.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.szymanski.sharelibrary.entity.Exchange;
import pl.szymanski.sharelibrary.entity.Requirement;
import pl.szymanski.sharelibrary.exceptions.requirements.RequirementAlreadyExists;
import pl.szymanski.sharelibrary.repositories.ports.RequirementRepository;
import pl.szymanski.sharelibrary.requests.CreateRequirementRequest;
import pl.szymanski.sharelibrary.services.ports.ExchangeService;
import pl.szymanski.sharelibrary.services.ports.RequirementService;
import pl.szymanski.sharelibrary.services.ports.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequirementServiceImpl implements RequirementService {

    private final RequirementRepository requirementRepository;
    private final UserService userService;
    private final ExchangeService exchangeService;

    @Override
    @Transactional
    public Requirement createRequirement(CreateRequirementRequest requirementRequest) {
        requirementRepository.getRequirements().stream().filter((it) ->
                it.getExchange().getId().equals(requirementRequest.getExchangeId()) && it.getUser().getId().equals(requirementRequest.getUserId())
        ).findAny().orElseThrow(RequirementAlreadyExists::new);
        Requirement requirement = new Requirement();
        requirement.setUser(userService.getUserById(requirementRequest.getUserId()));
        requirement.setActual(true);
        requirement.setExchange(exchangeService.getExchangeById(requirementRequest.getExchangeId()));
        requirement.setCreateTime(LocalDateTime.now());
        return requirementRepository.saveRequirement(requirement);
    }

    @Override
    public void cancelRequirement(Long id) {

    }

    @Override
    public List<Requirement> getUserRequirements(Long userId) {
        List<Exchange> userExchanges = exchangeService.getStartedExchanges().stream().filter(exchange -> exchange.getUser().getId().equals(userId)).collect(Collectors.toList());
        List<Requirement> requirements = new ArrayList<>();
        userExchanges.forEach(exchange -> requirements.addAll(requirementRepository.getRequirementByExchange(exchange.getId())));
        return requirements;
    }
}

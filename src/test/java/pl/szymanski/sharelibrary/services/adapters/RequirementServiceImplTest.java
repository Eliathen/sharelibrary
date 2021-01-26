package pl.szymanski.sharelibrary.services.adapters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.szymanski.sharelibrary.entity.Exchange;
import pl.szymanski.sharelibrary.entity.Requirement;
import pl.szymanski.sharelibrary.exceptions.requirements.RequirementAlreadyExists;
import pl.szymanski.sharelibrary.repositories.ports.RequirementRepository;
import pl.szymanski.sharelibrary.requests.CreateRequirementRequest;
import pl.szymanski.sharelibrary.services.ports.ExchangeService;
import pl.szymanski.sharelibrary.services.ports.UserService;
import pl.szymanski.sharelibrary.utils.generator.ExchangeGenerator;
import pl.szymanski.sharelibrary.utils.generator.RequirementGenerator;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequirementServiceImplTest {

    @Mock
    private RequirementRepository requirementRepository;
    @Mock
    private UserService userService;
    @Mock
    private ExchangeService exchangeService;
    @InjectMocks
    private RequirementServiceImpl requirementService;

    @Test
    void shouldThrowExceptionRequirementAlreadyExists() {
        //given
        CreateRequirementRequest request = RequirementGenerator.getCreateRequirementRequest();
        Requirement requirement = RequirementGenerator.getRequirement();
        when(requirementRepository.getRequirements())
                .thenReturn(List.of(requirement));
        //when & then
        Assertions.assertThatThrownBy(() ->
                requirementService.createRequirement(request)
        ).isInstanceOf(RequirementAlreadyExists.class);
    }

    @Test
    void shouldReturnRequirement() {
        //given
        CreateRequirementRequest request = RequirementGenerator.getCreateRequirementRequest();
        Requirement requirement = RequirementGenerator.getRequirement();
        when(requirementRepository.getRequirements())
                .thenReturn(new ArrayList<>());
        when(userService.getUserById(request.getUserId()))
                .thenReturn(requirement.getUser());
        when(exchangeService.getExchangeById(request.getExchangeId()))
                .thenReturn(requirement.getExchange());
        when(requirementRepository.saveRequirement(any()))
                .thenReturn(requirement);
        //when
        Requirement result = requirementService.createRequirement(request);
        //then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getExchange().getId()).isEqualTo(request.getExchangeId());
        Assertions.assertThat(result.getUser().getId()).isEqualTo(request.getUserId());
    }

    @Test
    void shouldReturnListOfRequestWithOneElement() {
        Exchange exchange = ExchangeGenerator.getExchange();
        List<Requirement> requirements = List.of(RequirementGenerator.getRequirement());
        exchange.setRequirements(requirements);
        when(exchangeService.getExchangesByUserId(exchange.getUser().getId()))
                .thenReturn(List.of(exchange));
        when(requirementRepository.getRequirementByExchange(exchange.getId())).thenReturn(
                requirements
        );
        //when
        List<Requirement> result = requirementService.getUserRequirements(1L);
        //then
        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result.size()).isEqualTo(1);
    }
}
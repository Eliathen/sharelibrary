package pl.szymanski.sharelibrary.services.ports;

import pl.szymanski.sharelibrary.entity.Requirement;
import pl.szymanski.sharelibrary.requests.CreateRequirementRequest;

import java.util.List;

public interface RequirementService {

    Requirement createRequirement(CreateRequirementRequest createRequirementRequest);

    void cancelRequirement(Long id);

    List<Requirement> getUserRequirements(Long userId);

}

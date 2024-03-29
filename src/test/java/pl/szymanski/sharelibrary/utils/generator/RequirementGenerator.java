package pl.szymanski.sharelibrary.utils.generator;

import pl.szymanski.sharelibrary.entity.Requirement;
import pl.szymanski.sharelibrary.requests.CreateRequirementRequest;
import pl.szymanski.sharelibrary.response.RequirementResponse;

import java.time.LocalDateTime;

public class RequirementGenerator {

    public static Requirement getRequirement() {
        Requirement requirement = new Requirement();
        requirement.setUser(UserGenerator.getUser());
        requirement.setCreateTime(LocalDateTime.now());
        requirement.setActual(true);
        requirement.setId(1L);
        requirement.setExchange(ExchangeGenerator.getExchange());

        return requirement;
    }

    public static RequirementResponse getRequirementResponse() {
        return RequirementResponse.of(getRequirement());
    }

    public static CreateRequirementRequest getCreateRequirementRequest() {
        return new CreateRequirementRequest(1L, 1L);
    }

}

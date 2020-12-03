package pl.szymanski.sharelibrary.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.szymanski.sharelibrary.entity.Requirement;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class RequirementResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "exchangeId")
    private ExchangeResponse exchange;

    @ManyToOne
    @JoinColumn(name = "userId")
    private BaseUserResponse user;

    private LocalDateTime createTime;

    private boolean isActual;

    public static RequirementResponse of(Requirement requirement) {
        return new RequirementResponse(
                requirement.getId(),
                ExchangeResponse.of(requirement.getExchange()),
                BaseUserResponse.of(requirement.getUser()),
                requirement.getCreateTime(),
                requirement.isActual()
        );
    }

}

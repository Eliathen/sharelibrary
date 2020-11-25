package pl.szymanski.sharelibrary.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.szymanski.sharelibrary.entity.Exchange;
import pl.szymanski.sharelibrary.enums.ExchangeType;

@AllArgsConstructor
@Data
public class ExchangeResponse {

    private Long id;

    private Boolean isFinished;

    private ExchangeType exchangeType;

    private Double deposit;

    private BookWithoutUsersResponse book;

    private BaseUserResponse user;

    private CoordinatesResponse coordinates;

    public static ExchangeResponse of(Exchange exchange) {
        return new ExchangeResponse(
                exchange.getId(),
                exchange.getIsFinished(),
                exchange.getExchangeType(),
                exchange.getDeposit(),
                BookWithoutUsersResponse.of(exchange.getBook()),
                BaseUserResponse.of(exchange.getUser()),
                CoordinatesResponse.of(exchange.getCoordinates())
        );
    }

}

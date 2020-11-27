package pl.szymanski.sharelibrary.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.szymanski.sharelibrary.entity.Exchange;
import pl.szymanski.sharelibrary.enums.ExchangeStatus;

@AllArgsConstructor
@Data
public class ExchangeResponse {

    private Long id;

    private ExchangeStatus exchangeStatus;

    private Double deposit;

    private BookWithoutUsersResponse book;

    private BaseUserResponse user;

    private CoordinatesResponse coordinates;

    public static ExchangeResponse of(Exchange exchange) {
        return new ExchangeResponse(
                exchange.getId(),
                exchange.getExchangeStatus(),
                exchange.getDeposit(),
                BookWithoutUsersResponse.of(exchange.getBook()),
                BaseUserResponse.of(exchange.getUser()),
                CoordinatesResponse.of(exchange.getCoordinates())
        );
    }

}

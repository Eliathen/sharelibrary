package pl.szymanski.sharelibrary.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.szymanski.sharelibrary.entity.Exchange;
import pl.szymanski.sharelibrary.entity.UserBook;
import pl.szymanski.sharelibrary.enums.ExchangeStatus;

@AllArgsConstructor
@Data
public class ExchangeResponse {

    private Long id;

    private ExchangeStatus exchangeStatus;

    private Double deposit;

    private UserBookResponse book;

    private BaseUserResponse user;

    private CoordinatesResponse coordinates;

    public static ExchangeResponse of(Exchange exchange) {
        return new ExchangeResponse(
                exchange.getId(),
                exchange.getExchangeStatus(),
                exchange.getDeposit(),
                UserBookResponse.of(exchange.getUser().getBooks()
                        .stream()
                        .filter(it -> it.getBook().getId().equals(exchange.getBook().getId()))
                        .findFirst().orElse(new UserBook(null, null, null, null))),
                BaseUserResponse.of(exchange.getUser()),
                CoordinatesResponse.of(exchange.getCoordinates())
        );
    }

}

package pl.szymanski.sharelibrary.commanddata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import pl.szymanski.sharelibrary.enums.ExchangeType;

@Data
public class AddExchangeRequest {

    private ExchangeType exchangeType;

    private Double deposit;

    private Long bookId;

    private Long userId;

    private CoordinatesRequest coordinates;

    @JsonCreator
    public AddExchangeRequest(@JsonProperty("exchangeType") ExchangeType exchangeType,
                              @JsonProperty("deposit") Double deposit,
                              @JsonProperty("bookId") Long bookId,
                              @JsonProperty("userId") Long userId,
                              @JsonProperty("coordinates") CoordinatesRequest coordinates) {
        this.exchangeType = exchangeType;
        this.deposit = deposit;
        this.bookId = bookId;
        this.userId = userId;
        this.coordinates = coordinates;
    }
}

package pl.szymanski.sharelibrary.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RemoveBookFromUserRequest {

    private Long userId;

    private Long bookId;

    @JsonCreator
    public RemoveBookFromUserRequest(@JsonProperty(value = "userId", required = true) Long userId,
                                     @JsonProperty(value = "bookId", required = true) Long bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }
}

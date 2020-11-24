package pl.szymanski.sharelibrary.commanddata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
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

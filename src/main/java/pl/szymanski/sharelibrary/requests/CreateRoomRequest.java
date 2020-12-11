package pl.szymanski.sharelibrary.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateRoomRequest {
    private Long senderId;
    private Long recipientId;

    @JsonCreator
    public CreateRoomRequest(@JsonProperty(value = "senderId", required = true) Long senderId,
                             @JsonProperty(value = "recipientId", required = true) Long recipientId) {
        this.senderId = senderId;
        this.recipientId = recipientId;
    }
}

package pl.szymanski.sharelibrary.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessageRequest {

    private Long chatId;

    private Long senderId;

    private Long recipientId;

    private String content;

    private LocalDateTime timestamp;

    @JsonCreator
    public ChatMessageRequest(@JsonProperty(value = "chatId", required = true) Long chatId,
                              @JsonProperty(value = "senderId", required = true) Long senderId,
                              @JsonProperty(value = "recipientId", required = true) Long recipientId,
                              @JsonProperty(value = "content", required = true) String content,
                              @JsonProperty(value = "timestamp", required = true) LocalDateTime timestamp) {
        this.chatId = chatId;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.content = content;
        this.timestamp = timestamp;
    }
}

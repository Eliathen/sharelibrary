package pl.szymanski.sharelibrary.utils.generator;

import pl.szymanski.sharelibrary.entity.ChatMessage;
import pl.szymanski.sharelibrary.entity.ChatRoom;
import pl.szymanski.sharelibrary.entity.User;
import pl.szymanski.sharelibrary.response.BaseUserResponse;
import pl.szymanski.sharelibrary.response.ChatMessageResponse;
import pl.szymanski.sharelibrary.response.ChatRoomResponse;

import java.time.LocalDateTime;

public class ChatGenerator {

    public static ChatRoomResponse getChatRoomResponse() {
        return ChatRoomResponse.of(getChatRoom());
    }

    public static ChatRoom getChatRoom() {
        ChatRoom room = new ChatRoom();
        room.setId(1L);
        User sender = UserGenerator.getUser();
        User recipient = UserGenerator.getUser();
        recipient.setId(1L);
        room.setSender(sender);
        room.setRecipient(recipient);
        return room;
    }

    public static ChatMessage getChatMessage() {
        User sender = UserGenerator.getUser();
        User recipient = UserGenerator.getUser();
        recipient.setId(1L);
        ChatMessage message = new ChatMessage();
        message.setChat(getChatRoom());
        message.setTimestamp(LocalDateTime.now());
        message.setContent("This is test message");
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setChat(getChatRoom());
        return message;
    }

    public static ChatMessageResponse getChatMessageResponse() {
        return new ChatMessageResponse(
                1L,
                ChatRoomResponse.of(getChatRoom()),
                BaseUserResponse.of(UserGenerator.getUser()),
                BaseUserResponse.of(UserGenerator.getUser()),
                "Message",
                LocalDateTime.now()

        );
    }

}

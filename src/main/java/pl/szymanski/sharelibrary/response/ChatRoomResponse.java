package pl.szymanski.sharelibrary.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.szymanski.sharelibrary.entity.ChatRoom;

@Data
@AllArgsConstructor
public class ChatRoomResponse {

    private Long id;

    private UserResponse sender;

    private UserResponse recipient;

    public static ChatRoomResponse of(ChatRoom chatRoom) {
        return new ChatRoomResponse(
                chatRoom.getId(),
                UserResponse.of(chatRoom.getSender()),
                UserResponse.of(chatRoom.getRecipient())
        );
    }
}

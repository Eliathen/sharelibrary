package pl.szymanski.sharelibrary.services.ports;

import pl.szymanski.sharelibrary.entity.ChatMessage;
import pl.szymanski.sharelibrary.entity.ChatRoom;

import java.util.List;
import java.util.Optional;

public interface ChatRoomService {

    ChatRoom createRoom(Long senderId, Long recipientId);

    List<ChatMessage> getMessageFromRoom(Long roomId);

    List<ChatRoom> getRoomByUserId(Long userId);

    Optional<ChatRoom> getRoomBySenderIdAndRecipientId(Long senderId, Long recipientId);
}

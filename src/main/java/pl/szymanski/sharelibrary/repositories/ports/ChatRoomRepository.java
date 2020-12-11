package pl.szymanski.sharelibrary.repositories.ports;

import pl.szymanski.sharelibrary.entity.ChatRoom;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository {

    Optional<ChatRoom> findBySenderIdAndRecipientId(Long senderId, Long recipientId);

    ChatRoom createRoom(ChatRoom chatRoom);

    Optional<ChatRoom> findByRoomId(Long roomId);

    List<ChatRoom> findAll();

}

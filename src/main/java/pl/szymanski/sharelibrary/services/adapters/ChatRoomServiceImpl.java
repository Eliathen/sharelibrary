package pl.szymanski.sharelibrary.services.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szymanski.sharelibrary.entity.ChatMessage;
import pl.szymanski.sharelibrary.entity.ChatRoom;
import pl.szymanski.sharelibrary.entity.User;
import pl.szymanski.sharelibrary.exceptions.chat.RoomNotExist;
import pl.szymanski.sharelibrary.repositories.ports.ChatRoomRepository;
import pl.szymanski.sharelibrary.services.ports.ChatRoomService;
import pl.szymanski.sharelibrary.services.ports.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserService userService;

    @Override
    public ChatRoom createRoom(Long senderId, Long recipientId) {
        ChatRoom chatRoom = new ChatRoom();
        User sender = userService.getUserById(senderId);
        User recipient = userService.getUserById(recipientId);
        chatRoom.setSender(sender);
        chatRoom.setRecipient(recipient);
        return chatRoomRepository.createRoom(chatRoom);
    }

    @Override
    public List<ChatMessage> getMessageFromRoom(Long roomId) {
        return getRoomById(roomId).getMessages();
    }

    @Override
    public List<ChatRoom> getRoomByUserId(Long userId) {
        return chatRoomRepository.findAll()
                .stream()
                .filter(it -> it.getRecipient().getId().equals(userId) || it.getSender().getId().equals(userId))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ChatRoom> getRoomBySenderIdAndRecipientId(Long senderId, Long recipientId) {
        return chatRoomRepository.findBySenderIdAndRecipientId(senderId, recipientId);
    }

    private ChatRoom getRoomById(Long roomId) {
        return chatRoomRepository.findByRoomId(roomId).orElseThrow(() -> new RoomNotExist(roomId));
    }

}

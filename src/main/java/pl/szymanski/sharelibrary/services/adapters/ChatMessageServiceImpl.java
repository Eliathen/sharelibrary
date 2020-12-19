package pl.szymanski.sharelibrary.services.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szymanski.sharelibrary.entity.ChatMessage;
import pl.szymanski.sharelibrary.entity.ChatRoom;
import pl.szymanski.sharelibrary.entity.User;
import pl.szymanski.sharelibrary.repositories.ports.ChatMessageRepository;
import pl.szymanski.sharelibrary.requests.ChatMessageRequest;
import pl.szymanski.sharelibrary.services.ports.ChatMessageService;
import pl.szymanski.sharelibrary.services.ports.ChatRoomService;
import pl.szymanski.sharelibrary.services.ports.UserService;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomService chatRoomService;
    private final UserService userService;

    @Override
    public ChatMessage saveMessage(ChatMessageRequest chatMessage) {
        return chatMessageRepository.save(prepareMessage(chatMessage));
    }

    private ChatMessage prepareMessage(ChatMessageRequest chatMessage) {
        User sender = userService.getUserById(chatMessage.getSenderId());
        User recipient = userService.getUserById(chatMessage.getRecipientId());

        ChatRoom chatRoom = getChatRoom(sender, recipient);
//        ChatRoom chatRoom =
//                (chatRoomService.getRoomBySenderIdAndRecipientId(sender.getId(), recipient.getId())
//                        .orElseGet(() ->
//                                chatRoomService.createRoom(chatMessage.getSenderId(), chatMessage.getRecipientId())
//                        ));
        ChatMessage message = new ChatMessage();
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setChat(chatRoom);
        message.setContent(chatMessage.getContent());
        message.setTimestamp(LocalDateTime.now());
        return message;
    }

    private ChatRoom getChatRoom(User sender, User recipient) {
        Optional<ChatRoom> room = chatRoomService.getRoomBySenderIdAndRecipientId(sender.getId(), recipient.getId());
        ChatRoom chatRoom;
        if (room.isEmpty()) {
            room = chatRoomService.getRoomBySenderIdAndRecipientId(recipient.getId(), sender.getId());
            if (room.isEmpty()) {
                chatRoom = chatRoomService.createRoom(sender.getId(), recipient.getId());
            } else {
                chatRoom = room.get();
            }
        } else {
            chatRoom = room.get();
        }
        return chatRoom;
    }
}

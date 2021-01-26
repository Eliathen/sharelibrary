package pl.szymanski.sharelibrary.services.adapters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.szymanski.sharelibrary.entity.ChatMessage;
import pl.szymanski.sharelibrary.entity.ChatRoom;
import pl.szymanski.sharelibrary.entity.User;
import pl.szymanski.sharelibrary.repositories.ports.ChatRoomRepository;
import pl.szymanski.sharelibrary.services.ports.UserService;
import pl.szymanski.sharelibrary.utils.generator.ChatGenerator;
import pl.szymanski.sharelibrary.utils.generator.UserGenerator;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChatRoomServiceImplTest {


    @Mock
    ChatRoomRepository chatRoomRepository;

    @Mock
    UserService userService;

    @InjectMocks
    ChatRoomServiceImpl chatRoomService;

    @Test
    void shouldCreateRoom() {
        //given
        Long senderId = 1L;
        Long recipientId = 2L;
        when(userService.getUserById(1L)).thenReturn(UserGenerator.getUser());
        User secondUser = UserGenerator.getUser();
        secondUser.setId(2L);
        when(chatRoomRepository.createRoom(any())).thenReturn(ChatGenerator.getChatRoom());
        //when
        ChatRoom result = chatRoomService.createRoom(senderId, recipientId);
        //then
        Assertions.assertThat(result.getSender().getId()).isEqualTo(senderId);
        Assertions.assertThat(result.getRecipient().getId()).isEqualTo(recipientId);
    }

    @Test
    void shouldReturnListOfMessageWithOneElement() {
        //given
        ChatMessage message = ChatGenerator.getChatMessage();
        ChatRoom chatRoom = ChatGenerator.getChatRoom();
        chatRoom.setMessages(List.of(message));
        when(chatRoomRepository.findByRoomId(chatRoom.getId()))
                .thenReturn(Optional.of(chatRoom));
        //when
        List<ChatMessage> result = chatRoomService.getMessageFromRoom(chatRoom.getId());
        //then
        Assertions.assertThat(result).hasSize(1);
    }

    @Test
    void shouldReturnListOfChatRoomWithOneElement() {
        //given
        ChatMessage message = ChatGenerator.getChatMessage();
        ChatRoom chatRoom = ChatGenerator.getChatRoom();
        when(chatRoomRepository.findAll()).thenReturn(List.of(chatRoom));
        //when
        List<ChatRoom> result = chatRoomService.getRoomByUserId(chatRoom.getSender().getId());
        //then
        Assertions.assertThat(result).hasSize(1);
    }

    @Test
    void shouldReturnChatRoomByGivenSenderIdAndRecipientId() {
        //given
        ChatMessage message = ChatGenerator.getChatMessage();
        ChatRoom chatRoom = ChatGenerator.getChatRoom();
        when(chatRoomRepository.findBySenderIdAndRecipientId(chatRoom.getSender().getId(),
                chatRoom.getRecipient().getId()))
                .thenReturn(Optional.of(chatRoom));
        //when
        ChatRoom result = chatRoomService.getRoomBySenderIdAndRecipientId(
                chatRoom.getSender().getId(),
                chatRoom.getRecipient().getId()
        );
        //then
        Assertions.assertThat(result.getId()).isEqualTo(chatRoom.getId());
    }

}
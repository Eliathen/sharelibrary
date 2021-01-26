package pl.szymanski.sharelibrary.services.adapters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.szymanski.sharelibrary.entity.ChatMessage;
import pl.szymanski.sharelibrary.entity.User;
import pl.szymanski.sharelibrary.repositories.ports.ChatMessageRepository;
import pl.szymanski.sharelibrary.requests.ChatMessageRequest;
import pl.szymanski.sharelibrary.utils.generator.ChatGenerator;
import pl.szymanski.sharelibrary.utils.generator.UserGenerator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChatMessageServiceImplTest {

    @Mock
    ChatMessageRepository chatMessageRepository;
    @Mock
    UserServiceImpl userService;
    @Mock
    ChatRoomServiceImpl chatRoomService;
    @InjectMocks
    ChatMessageServiceImpl chatMessageService;

    @Test
    void shouldAddChatMessage() {
        //given
        ChatMessageRequest chatMessageRequest = ChatGenerator.getChatMessageRequest();
        when(chatMessageRepository.save(any())).thenReturn(ChatGenerator.getChatMessage());
        when(userService.getUserById(1L)).thenReturn(UserGenerator.getUser());
        User second = UserGenerator.getUser();
        second.setId(2L);
        when(userService.getUserById(2L)).thenReturn(second);
        when(chatRoomService.getRoomBySenderIdAndRecipientId(1L, 2L)).thenReturn(ChatGenerator.getChatRoom());

        //when
        ChatMessage result = chatMessageService.saveMessage(chatMessageRequest);
        //then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getRecipient().getId()).isEqualTo(chatMessageRequest.getRecipientId());
        Assertions.assertThat(result.getSender().getId()).isEqualTo(chatMessageRequest.getSenderId());
        Assertions.assertThat(result.getContent()).isEqualTo(chatMessageRequest.getContent());

    }
}
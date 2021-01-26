package pl.szymanski.sharelibrary.repositories.adapters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.szymanski.sharelibrary.entity.ChatMessage;
import pl.szymanski.sharelibrary.entity.ChatRoom;
import pl.szymanski.sharelibrary.entity.User;
import pl.szymanski.sharelibrary.repositories.jpa.ChatMessageJPARepository;
import pl.szymanski.sharelibrary.repositories.jpa.ChatRoomJPARepository;
import pl.szymanski.sharelibrary.repositories.jpa.UserJPARepository;
import pl.szymanski.sharelibrary.repositories.ports.ChatMessageRepository;
import pl.szymanski.sharelibrary.utils.generator.ChatGenerator;
import pl.szymanski.sharelibrary.utils.generator.UserGenerator;

import static org.mockito.Mockito.times;

@SpringBootTest
class ChatMessageRepositoryImplTest {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ChatMessageJPARepository chatMessageJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private ChatRoomJPARepository chatRoomJPARepository;

    @BeforeEach
    void setUp() {
        chatMessageJPARepository.deleteAll();
        chatRoomJPARepository.deleteAll();
        userJPARepository.deleteAll();
    }

    @AfterEach
    void cleanUp() {
        chatMessageJPARepository.deleteAll();
        chatRoomJPARepository.deleteAll();
        userJPARepository.deleteAll();
    }

    @Test
    void shouldReturnChatMessage() {
        //given
        User user = userJPARepository.saveAndFlush(UserGenerator.getUser());
        ChatMessage chatMessage = ChatGenerator.getChatMessage();
        ChatRoom chatRoom = ChatGenerator.getChatRoom();
        chatRoom.setRecipient(user);
        chatRoom.setSender(user);
        chatRoom = chatRoomJPARepository.saveAndFlush(chatRoom);
        chatMessage.setChat(chatRoom);
        chatMessage.setSender(user);
        chatMessage.setRecipient(user);
        //when
        ChatMessage result = chatMessageRepository.save(chatMessage);
        //then
        Assertions.assertThat(result.getRecipient().getId()).isEqualTo(chatMessage.getRecipient().getId());
        Assertions.assertThat(result.getSender().getId()).isEqualTo(chatMessage.getSender().getId());
        Assertions.assertThat(result.getContent()).isEqualTo(chatMessage.getContent());

    }

    @Test
    void shouldCallSaveMethodFromJpaRepository() {
        //given
        ChatMessage chatMessage = ChatGenerator.getChatMessage();
        ChatMessageJPARepository chatMessageJPARepository = Mockito.mock(ChatMessageJPARepository.class);
        ChatMessageRepository repository = new ChatMessageRepositoryImpl(chatMessageJPARepository);
        //when
        repository.save(chatMessage);
        //then
        Mockito.verify(chatMessageJPARepository, times(1))
                .save(chatMessage);

    }


}
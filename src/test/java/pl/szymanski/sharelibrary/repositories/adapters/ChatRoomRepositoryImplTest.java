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
import pl.szymanski.sharelibrary.repositories.ports.ChatRoomRepository;
import pl.szymanski.sharelibrary.utils.generator.ChatGenerator;
import pl.szymanski.sharelibrary.utils.generator.UserGenerator;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;

@SpringBootTest
class ChatRoomRepositoryImplTest {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

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
    void should_return_room_by_sender_id_and_recipient_id() {
        //given
        User user = userJPARepository.saveAndFlush(UserGenerator.getUser());
        ChatMessage chatMessage = ChatGenerator.getChatMessage();
        ChatRoom chatRoom = ChatGenerator.getChatRoom();
        chatRoom.setRecipient(user);
        chatRoom.setSender(user);
        chatRoomJPARepository.saveAndFlush(chatRoom);
        //when
        Optional<ChatRoom> result = chatRoomRepository.findBySenderIdAndRecipientId(user.getId(), user.getId());
        //then
        Assertions.assertThat(result.isPresent()).isEqualTo(true);
        Assertions.assertThat(result.get().getSender().getId()).isEqualTo(user.getId());
        Assertions.assertThat(result.get().getRecipient().getId()).isEqualTo(user.getId());

    }

    @Test
    void should_call_find_by_sender_id_and_recipient_id_method_from_jpa_repository() {
        //given
        ChatMessage chatMessage = ChatGenerator.getChatMessage();
        ChatRoom chatRoom = ChatGenerator.getChatRoom();
        ChatRoomJPARepository chatRoomJPARepository = Mockito.mock(ChatRoomJPARepository.class);
        ChatRoomRepository repository = new ChatRoomRepositoryImpl(chatRoomJPARepository);
        //when
        repository.findBySenderIdAndRecipientId(chatMessage.getSender().getId(), chatMessage.getRecipient().getId());
        //then
        Mockito.verify(chatRoomJPARepository, times(1))
                .findBySenderIdAndRecipientId(chatMessage.getSender().getId(), chatMessage.getRecipient().getId());

    }

    @Test
    void should_return_room() {
        //given
        User user = userJPARepository.saveAndFlush(UserGenerator.getUser());
        ChatMessage chatMessage = ChatGenerator.getChatMessage();
        ChatRoom chatRoom = ChatGenerator.getChatRoom();
        chatRoom.setRecipient(user);
        chatRoom.setSender(user);
        //when
        ChatRoom result = chatRoomRepository.createRoom(chatRoom);
        //then
        Assertions.assertThat(result.getSender().getId()).isEqualTo(user.getId());
        Assertions.assertThat(result.getRecipient().getId()).isEqualTo(user.getId());

    }

    @Test
    void should_call_save_and_flush_method_from_jpa_repository() {
        //given
        ChatMessage chatMessage = ChatGenerator.getChatMessage();
        ChatRoom chatRoom = ChatGenerator.getChatRoom();
        ChatRoomJPARepository chatRoomJPARepository = Mockito.mock(ChatRoomJPARepository.class);
        ChatRoomRepository repository = new ChatRoomRepositoryImpl(chatRoomJPARepository);
        //when
        repository.createRoom(chatRoom);
        //then
        Mockito.verify(chatRoomJPARepository, times(1))
                .saveAndFlush(chatRoom);

    }

    @Test
    void should_return_room_by_id() {
        //given
        User user = userJPARepository.saveAndFlush(UserGenerator.getUser());
        ChatMessage chatMessage = ChatGenerator.getChatMessage();
        ChatRoom chatRoom = ChatGenerator.getChatRoom();
        chatRoom.setRecipient(user);
        chatRoom.setSender(user);
        chatRoom = chatRoomJPARepository.saveAndFlush(chatRoom);
        //when
        Optional<ChatRoom> result = chatRoomRepository.findByRoomId(chatRoom.getId());
        //then
        Assertions.assertThat(result.isPresent()).isEqualTo(true);
        Assertions.assertThat(result.get().getId()).isEqualTo(chatRoom.getId());

    }

    @Test
    void should_call_find_by_id_method_from_jpa_repository() {
        //given
        ChatMessage chatMessage = ChatGenerator.getChatMessage();
        ChatRoom chatRoom = ChatGenerator.getChatRoom();
        ChatRoomJPARepository chatRoomJPARepository = Mockito.mock(ChatRoomJPARepository.class);
        ChatRoomRepository repository = new ChatRoomRepositoryImpl(chatRoomJPARepository);
        //when
        repository.findByRoomId(chatRoom.getId());
        //then
        Mockito.verify(chatRoomJPARepository, times(1))
                .findById(chatRoom.getId());

    }

    @Test
    void should_return_rooms() {
        //given
        User user = userJPARepository.saveAndFlush(UserGenerator.getUser());
        ChatMessage chatMessage = ChatGenerator.getChatMessage();
        ChatRoom chatRoom = ChatGenerator.getChatRoom();
        chatRoom.setRecipient(user);
        chatRoom.setSender(user);
        chatRoom = chatRoomJPARepository.saveAndFlush(chatRoom);
        //when
        List<ChatRoom> result = chatRoomRepository.findAll();
        //then
        Assertions.assertThat(result.size()).isEqualTo(1);

    }

    @Test
    void should_call_find_all_method_from_jpa_repository() {
        //given
        ChatMessage chatMessage = ChatGenerator.getChatMessage();
        ChatRoom chatRoom = ChatGenerator.getChatRoom();
        ChatRoomJPARepository chatRoomJPARepository = Mockito.mock(ChatRoomJPARepository.class);
        ChatRoomRepository repository = new ChatRoomRepositoryImpl(chatRoomJPARepository);
        //when
        repository.findAll();
        //then
        Mockito.verify(chatRoomJPARepository, times(1))
                .findAll();

    }
}
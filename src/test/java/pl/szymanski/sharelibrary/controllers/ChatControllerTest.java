package pl.szymanski.sharelibrary.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.szymanski.sharelibrary.entity.ChatMessage;
import pl.szymanski.sharelibrary.entity.ChatRoom;
import pl.szymanski.sharelibrary.exceptions.chat.RoomNotExist;
import pl.szymanski.sharelibrary.response.ChatRoomResponse;
import pl.szymanski.sharelibrary.services.adapters.ChatRoomServiceImpl;
import pl.szymanski.sharelibrary.utils.generator.ChatGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WithMockUser(username = "z", password = "z")
@SpringBootTest
class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ChatRoomServiceImpl chatRoomService;

    @Test
    void should_return_list_of_chat_room_response_and_status_200() throws Exception {
        //given
        Long userId = 1L;
        List<ChatRoom> rooms = Arrays.asList(
                ChatGenerator.getChatRoom(),
                ChatGenerator.getChatRoom()
        );
        when(chatRoomService.getRoomByUserId(anyLong())).thenReturn(rooms);
        //when
        MvcResult result = mockMvc.perform(get("/api/v1/chat/rooms/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andReturn();
        //then
        List<ChatRoomResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });
        Assertions.assertThat(response).isNotEmpty();
        Assertions.assertThat(response.size()).isEqualTo(2);
        Assertions.assertThat(response.get(0).getId()).isEqualTo(1L);

    }

    @Test
    void should_return_empty_list_of_chat_room_response_and_status_200() throws Exception {
        //given
        Long userId = 1L;
        List<ChatRoom> rooms = new ArrayList<>();
        when(chatRoomService.getRoomByUserId(anyLong())).thenReturn(rooms);
        //when
        MvcResult result = mockMvc.perform(get("/api/v1/chat/rooms/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andReturn();
        //then
        List<ChatRoomResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });
        Assertions.assertThat(response).isEmpty();

    }

    @Test
    void should_return_list_of_chat_message_response_and_status_200() throws Exception {
        //given
        Long roomId = 1L;
        List<ChatMessage> messages = Arrays.asList(
                ChatGenerator.getChatMessage(),
                ChatGenerator.getChatMessage(),
                ChatGenerator.getChatMessage());
        when(chatRoomService.getMessageFromRoom(anyLong())).thenReturn(messages);
        //when
        MvcResult result = mockMvc.perform(get("/api/v1/chat/rooms/" + roomId + "/messages")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andReturn();
        //then
        List<ChatMessage> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });
        Assertions.assertThat(response.size()).isEqualTo(3);
        Assertions.assertThat(response.get(0).getSender().getId()).isEqualTo(1L);
        Assertions.assertThat(response.get(0).getRecipient().getId()).isEqualTo(1L);

    }

    @Test
    void should_throw_exception_room_not_exists_and_return_status_404() throws Exception {
        //given
        Long roomId = 3L;
        when(chatRoomService.getMessageFromRoom(roomId)).thenThrow(new RoomNotExist());
        //when
        MvcResult result = mockMvc.perform(
                get("/api/v1/chat/rooms/" + roomId + "/messages")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound())
                .andReturn();
        //then
        assertTrue(result.getResolvedException() instanceof RoomNotExist);
    }

    @Test
    void should_return_chat_room_response_and_status_200() throws Exception {
        //given
        Long firstUser = 1L;
        Long secondUser = 1L;
        when(chatRoomService.getRoomBySenderIdAndRecipientId(firstUser, secondUser))
                .thenReturn(ChatGenerator.getChatRoom());
        when(chatRoomService.getRoomBySenderIdAndRecipientId(secondUser, firstUser))
                .thenReturn(ChatGenerator.getChatRoom());
        //when
        MvcResult firstResult = mockMvc.perform(
                get("/api/v1/chat/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("sender", String.valueOf(firstUser))
                        .param("recipient", String.valueOf(secondUser))
        ).andExpect(status().isOk())
                .andReturn();

        MvcResult secondResult = mockMvc.perform(
                get("/api/v1/chat/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("recipient", String.valueOf(firstUser))
                        .param("sender", String.valueOf(secondUser))
        ).andExpect(status().isOk())
                .andReturn();
        //then
        ChatRoomResponse firstResponse = objectMapper.readValue(firstResult.getResponse().getContentAsString(), ChatRoomResponse.class);
        ChatRoomResponse secondResponse = objectMapper.readValue(secondResult.getResponse().getContentAsString(), ChatRoomResponse.class);

        Assertions.assertThat(firstResponse).isEqualTo(secondResponse);
        Assertions.assertThat(firstResponse.getSender().getId()).isEqualTo(firstUser);
        Assertions.assertThat(firstResponse.getRecipient().getId()).isEqualTo(secondUser);
        Assertions.assertThat(secondResponse.getSender().getId()).isEqualTo(firstUser);
        Assertions.assertThat(secondResponse.getRecipient().getId()).isEqualTo(secondUser);
    }
}
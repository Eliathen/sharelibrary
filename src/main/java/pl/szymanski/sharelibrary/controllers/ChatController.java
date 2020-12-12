package pl.szymanski.sharelibrary.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.szymanski.sharelibrary.entity.ChatMessage;
import pl.szymanski.sharelibrary.requests.ChatMessageRequest;
import pl.szymanski.sharelibrary.response.ChatMessageResponse;
import pl.szymanski.sharelibrary.response.ChatRoomResponse;
import pl.szymanski.sharelibrary.services.ports.ChatMessageService;
import pl.szymanski.sharelibrary.services.ports.ChatRoomService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping
    public void processMessage(@Payload ChatMessageRequest chatMessage) {
        ChatMessage message = chatMessageService.saveMessage(chatMessage);
        simpMessagingTemplate.convertAndSendToUser(
                message.getRecipient().getId().toString(), "queue/messages",
                ChatMessageResponse.of(message)
        );
    }

    @GetMapping("rooms/{user}")
    public @ResponseBody
    ResponseEntity<List<ChatRoomResponse>> getUserRooms(@PathVariable("user") Long userId) {
        return new ResponseEntity<>(
                chatRoomService.getRoomByUserId(userId).stream().map(ChatRoomResponse::of).collect(Collectors.toList()), HttpStatus.OK
        );
    }

    @GetMapping("rooms/{id}/messages")
    public @ResponseBody
    ResponseEntity<List<ChatMessageResponse>> getRoomMessages(@PathVariable("id") Long roomId) {
        return new ResponseEntity<>(
                chatRoomService.getMessageFromRoom(roomId).stream().map(ChatMessageResponse::of).collect(Collectors.toList()),
                HttpStatus.OK
        );
    }
}

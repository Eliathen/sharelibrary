package pl.szymanski.sharelibrary.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.szymanski.sharelibrary.response.ChatMessageResponse;
import pl.szymanski.sharelibrary.response.ChatRoomResponse;
import pl.szymanski.sharelibrary.services.ports.ChatRoomService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatRoomService chatRoomService;
//    private final ChatMessageService chatMessageService;
//    private final SimpMessagingTemplate simpMessagingTemplate;
//
//    @MessageMapping
//    public void processMessage(@Payload ChatMessageRequest chatMessage) {
//        System.out.println("handling send message: " + chatMessage.getContent() + " to: "+ chatMessage.getRecipientId());
//        ChatMessage message = chatMessageService.saveMessage(chatMessage);
//        simpMessagingTemplate.convertAndSendToUser(
//                message.getRecipient().getId().toString(), "queue/messages",
//                ChatMessageResponse.of(message)
//        );
//    }
//    @MessageMapping
//    @SendTo("/topic/messages")
//    public String processMessage(String message){
//        System.out.println("Message = " + message);
//        return "Hello";
//    }

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

package pl.szymanski.sharelibrary.services.ports;

import pl.szymanski.sharelibrary.entity.ChatMessage;
import pl.szymanski.sharelibrary.requests.ChatMessageRequest;

public interface ChatMessageService {

    ChatMessage saveMessage(ChatMessageRequest chatMessage);

}

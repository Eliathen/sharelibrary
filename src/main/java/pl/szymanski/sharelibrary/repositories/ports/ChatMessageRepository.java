package pl.szymanski.sharelibrary.repositories.ports;

import pl.szymanski.sharelibrary.entity.ChatMessage;

public interface ChatMessageRepository {

    ChatMessage save(ChatMessage chatMessage);

}

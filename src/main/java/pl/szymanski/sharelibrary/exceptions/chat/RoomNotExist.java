package pl.szymanski.sharelibrary.exceptions.chat;

import pl.szymanski.sharelibrary.exceptions.ExceptionMessages;

public class RoomNotExist extends RuntimeException {
    public RoomNotExist(Long roomId) {
        super(String.format(ExceptionMessages.CHAT_ROOM_NOT_EXIST_FORMAT, roomId));
    }
}

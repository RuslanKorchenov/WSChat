package ru.itis.services.interfaces;

import ru.itis.dto.MessageDto;

import java.util.List;

public interface MessageService {
    void saveMessage(MessageDto messageDto);
    List<MessageDto> getAll(String token);
}

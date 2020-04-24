package ru.itis.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dto.MessageDto;
import ru.itis.models.Message;
import ru.itis.models.Room;
import ru.itis.models.User;
import ru.itis.repositories.MessagesRepository;
import ru.itis.repositories.RoomsRepository;
import ru.itis.repositories.UsersRepository;
import ru.itis.services.interfaces.MessageService;

import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private RoomsRepository roomsRepository;

    @Autowired
    private MessagesRepository messagesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public void saveMessage(MessageDto messageDto) {

        Optional<Room> optionalRoom = roomsRepository.findByToken(messageDto.getRoomToken());
        Optional<User> optionalUser = usersRepository.find(messageDto.getUserId());
        if(optionalRoom.isPresent() && optionalUser.isPresent()){
            messagesRepository.save(Message.builder()
                    .room(optionalRoom.get())
                    .user(optionalUser.get())
                    .text(messageDto.getText())
                    .build());
        }
    }

    @Override
    public List<MessageDto> getAll(String token) {
        Optional<Room> optionalRoom = roomsRepository.findByToken(token);
        if(optionalRoom.isPresent()) {
            return from(messagesRepository.findByRoom(optionalRoom.get()));
        }
        return new ArrayList<>();
    }

    private List<MessageDto> from(List<Message> messages) {
        List<MessageDto> messageDtos = new ArrayList<>();
        for(Message message: messages) {
            messageDtos.add(MessageDto.builder()
            .roomToken(message.getRoom().getToken())
            .text(message.getText())
            .userId(message.getUser().getId())
            .build());
        }
        return messageDtos;
    }
}

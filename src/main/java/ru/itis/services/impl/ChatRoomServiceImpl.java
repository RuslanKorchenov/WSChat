package ru.itis.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.dto.RoomDto;
import ru.itis.models.Room;
import ru.itis.repositories.RoomsRepository;
import ru.itis.services.interfaces.ChatRoomService;

import java.util.*;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private static final Map<String, List<Long>> roomUsers = new HashMap<>();

    @Autowired
    private RoomsRepository roomsRepository;

    @Override
    public List<Room> getRooms() {
        return roomsRepository.findAll();
    }

    @Override
    public void createRoom(RoomDto roomDto) {
        roomsRepository.save(Room.builder()
                .name(roomDto.getName())
                .token(UUID.randomUUID().toString())
                .build());
    }

    @Override
    @Transactional
    public Room getRoom(String roomToken) {
        Optional<Room> roomOptional = roomsRepository.findByToken(roomToken);
        if (roomOptional.isPresent()) {
            return roomOptional.get();
        }
        return null;
    }
    @Override
    public void addIdToRoom(String roomToken, Long userId) {
        if(!roomUsers.containsKey(roomToken)) {
            roomUsers.put(roomToken, new ArrayList<>());
        }
        roomUsers.get(roomToken).add(userId);
    }

    @Override
    public List<Long> getUsersId(String roomToken) {
        return roomUsers.get(roomToken);
    }
}

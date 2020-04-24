package ru.itis.services.interfaces;

import ru.itis.dto.RoomDto;
import ru.itis.models.Room;

import java.util.List;

public interface ChatRoomService {
    List<Room> getRooms();
    void createRoom(RoomDto roomDto);
    void addIdToRoom(String roomToken, Long userId);
    List<Long> getUsersId(String roomToken);
    Room getRoom(String roomToken);
}

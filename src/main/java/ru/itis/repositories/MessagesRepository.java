package ru.itis.repositories;

import ru.itis.models.Message;
import ru.itis.models.Room;

import java.util.List;

public interface MessagesRepository extends CrudRepository<Long, Message> {
    List<Message> findByRoom(Room room);
}

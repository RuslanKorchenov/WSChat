package ru.itis.repositories;

import ru.itis.models.Room;

import java.util.List;
import java.util.Optional;

public interface RoomsRepository extends CrudRepository<Long, Room> {
    Optional<Room> findByToken(String token);
}

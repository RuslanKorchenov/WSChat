package ru.itis.repositories.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.models.Room;
import ru.itis.repositories.RoomsRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Component
public class RoomsRepositoryJpaImpl implements RoomsRepository {
    //language=HQL
    private final static String HQL_FIND_ALL = "SELECT room FROM Room room";
    //language=HQL
    private final static String HQL_FIND_BY_TOKEN = "SELECT room FROM Room room WHERE room.token =:token ";

    @PersistenceContext
    private EntityManager entityManagerFactory;

    @Override
    public Optional<Room> findByToken(String token) {
        Query query = entityManagerFactory.createQuery(HQL_FIND_BY_TOKEN, Room.class);
        query.setParameter("token", token);
        try {
            return Optional.of((Room) query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Room> find(Long id) {
        return Optional.ofNullable(entityManagerFactory.find(Room.class, id));
    }

    @Override
    public List<Room> findAll() {
        return entityManagerFactory.createQuery(HQL_FIND_ALL).getResultList();
    }

    @Override
    @Transactional
    public void save(Room entity) {
        entityManagerFactory.merge(entity);
    }

    @Override
    public void delete(Long id) {
        Room room = entityManagerFactory.find(Room.class, id);
        entityManagerFactory.remove(room);
    }
}

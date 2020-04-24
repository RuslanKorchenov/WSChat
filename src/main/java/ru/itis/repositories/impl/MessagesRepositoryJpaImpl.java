package ru.itis.repositories.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.models.Message;
import ru.itis.models.Room;
import ru.itis.repositories.MessagesRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Component
public class MessagesRepositoryJpaImpl implements MessagesRepository {
    //language=HQL
    private static final String HQL_FIND_ALL = "FROM Message message";
    //language=HQL
    private static final String HQL_FIND_BY_ROOM = "select message from Message message " +
            "join Room room on message.room = room where room.id =: id";

    @PersistenceContext
    private EntityManager entityManagerFactory;

    @Override
    public Optional<Message> find(Long id) {
        return Optional.ofNullable(entityManagerFactory.find(Message.class, id));
    }

    @Override
    public List<Message> findAll() {
        return entityManagerFactory.createQuery(HQL_FIND_ALL).getResultList();
    }


    @Override
    @Transactional
    public void save(Message entity) {
        entityManagerFactory.merge(entity);
    }

    @Override
    public void delete(Long id) {
        Message message = entityManagerFactory.find(Message.class, id);
        entityManagerFactory.remove(message);
    }

    @Override
    public List<Message> findByRoom(Room room) {
        Query query = entityManagerFactory.createQuery(HQL_FIND_BY_ROOM, Message.class);
        query.setParameter("id", room.getId());
        return query.getResultList();
    }
}

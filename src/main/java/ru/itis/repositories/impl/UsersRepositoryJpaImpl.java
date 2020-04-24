package ru.itis.repositories.impl;


import org.springframework.stereotype.Component;
import ru.itis.models.User;
import ru.itis.repositories.UsersRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class UsersRepositoryJpaImpl implements UsersRepository {
    //language=HQL
    private final static String HQL_FIND_ALL = "SELECT user FROM User user";
    //language=HQL
    private final static String HQL_FIND_BY_EMAIL = "SELECT user FROM User user WHERE user.email =:email ";

    @PersistenceContext
    private EntityManager entityManagerFactory;

    @Override
    public Optional<User> findByEmail(String email) {
        Query query = entityManagerFactory.createQuery(HQL_FIND_BY_EMAIL, User.class);
        query.setParameter("email", email);
        try {
            return Optional.of((User)query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> find(Long id) {
        return Optional.ofNullable(entityManagerFactory.find(User.class, id));
    }

    @Override
    public List<User> findAll() {
        return entityManagerFactory.createQuery(HQL_FIND_ALL).getResultList();
    }

    @Override
    @Transactional
    public void save(User user) {
        entityManagerFactory.merge(user);
    }

    @Override
    public void delete(Long id) {
        User user = entityManagerFactory.find(User.class, id);
        entityManagerFactory.remove(user);
    }
}

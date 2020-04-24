package ru.itis.repositories.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.models.CookieValue;
import ru.itis.models.Message;
import ru.itis.models.User;
import ru.itis.repositories.CookieValuesRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Component
public class CookieValuesRepositoryJpaImpl implements CookieValuesRepository {
    //language=HQL
    private static final String HQL_FIND_ALL = "FROM CookieValue cookie";
    //language=HQL
    private final static String HQL_FIND_BY_VALUE = "SELECT cookie FROM CookieValue cookie WHERE cookie.value =:value ";
    @PersistenceContext
    private EntityManager entityManagerFactory;

    @Override
    public Optional<CookieValue> find(Long id) {
        return Optional.ofNullable(entityManagerFactory.find(CookieValue.class, id));
    }

    @Override
    public List<CookieValue> findAll() {
        return entityManagerFactory.createQuery(HQL_FIND_ALL).getResultList();
    }

    @Override
    @Transactional
    public void save(CookieValue entity) {
        entityManagerFactory.merge(entity);
    }

    @Override
    public void delete(Long id) {
        CookieValue cookieValue = entityManagerFactory.find(CookieValue.class, id);
        entityManagerFactory.remove(cookieValue);
    }

    @Override
    public Optional<CookieValue> findByValue(String value) {
        Query query = entityManagerFactory.createQuery(HQL_FIND_BY_VALUE, CookieValue.class);
        query.setParameter("value", value);
        try {
            return Optional.of((CookieValue) query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}

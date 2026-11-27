package com.javarush.khmelov.repository;

import com.javarush.lesson09.SessionCreator;
import jakarta.persistence.Transient;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class BaseRepository<T> implements Repository<T>, AutoCloseable {

    private final SessionCreator sessionCreator;
    private final Class<T> entityClass;

    public BaseRepository(Class<T> entityClass, SessionCreator sessionCreator) {
        this.entityClass = entityClass;
        this.sessionCreator = sessionCreator;
    }

    //HQL
    @Override
    public Collection<T> getAll() {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            Query<T> query = session.createQuery("select e from %s e".formatted(entityClass.getSimpleName()), entityClass);
            List<T> list = query.list();
            transaction.commit();
            return list;
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    /* session->cb->cq->root
     * c <- filter fields and add cb.equals(root.get(name), value)
     * cq.select(root).where(predicates);
     * result <- session.createQuery(cq).list(); */
    @Override
    public Stream<T> find(T pattern) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            HibernateCriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            JpaCriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
            JpaRoot<T> root = criteriaQuery.from(entityClass);
            Field[] fields = pattern.getClass().getDeclaredFields();
            List<Predicate> predicates = new ArrayList<>();
            for (Field field : fields) {
                if (!Modifier.isStatic(field.getModifiers()) && field.trySetAccessible()) {
                    Object value = field.get(pattern);
                    if (value != null && field.getAnnotation(Transient.class) == null) {
                        String fieldName = field.getName();
                        Predicate predicate = criteriaBuilder.equal(root.get(fieldName), value);
                        predicates.add(predicate);
                    }
                }
            }
            Predicate[] array = predicates.toArray(new Predicate[0]);
            criteriaQuery
                    .select(root)
                    .distinct(true)
                    .where(array)
                    .orderBy(criteriaBuilder.desc(root.get("id")));
            Query<T> userQuery = session.createQuery(criteriaQuery);
            List<T> users = userQuery.list();
            transaction.commit();
            return users.stream();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public T get(long id) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            T user = session.find(entityClass, id);
            transaction.commit();
            return user;
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(T entity) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(T entity) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(T entity) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        sessionCreator.close();
    }
}

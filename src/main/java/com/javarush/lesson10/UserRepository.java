package com.javarush.lesson10;

import com.javarush.khmelov.entity.Role;
import com.javarush.khmelov.entity.User;
import com.javarush.khmelov.repository.Repository;
import com.javarush.lesson09.SessionCreator;
import jakarta.persistence.Transient;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaPredicate;
import org.hibernate.query.criteria.JpaRoot;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class UserRepository implements Repository<User>, AutoCloseable {

    private final SessionCreator sessionCreator;

    public UserRepository() {
        sessionCreator = new SessionCreator();
    }

    //HQL
    @Override
    public Collection<User> getAll() {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            Query<User> query = session.createQuery("select u from User u", User.class);
            List<User> list = query.list();
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
    public Stream<User> find(User pattern) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            HibernateCriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            JpaCriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            JpaRoot<User> root = criteriaQuery.from(User.class);
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
            Query<User> userQuery = session.createQuery(criteriaQuery);
            List<User> users = userQuery.list();
            transaction.commit();
            return users.stream();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public User get(long id) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            User user = session.find(User.class, 1L);
            transaction.commit();
            return user;
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(User user) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.merge(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(User user) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.remove(user);
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

    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        try (userRepository) {
            Collection<User> all = userRepository.getAll();
            all.forEach(System.out::println);
            System.out.println("=".repeat(20));
            User pattern = User.builder().login("Carl").role(Role.USER).build();
            Stream<User> userStream = userRepository.find(pattern);
            userStream.forEach(System.out::println);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

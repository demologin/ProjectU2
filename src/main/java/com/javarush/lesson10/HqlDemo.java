package com.javarush.lesson10;

import com.javarush.khmelov.entity.Role;
import com.javarush.khmelov.entity.User;
import com.javarush.lesson09.SessionCreator;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;

import java.util.List;
import java.util.stream.Stream;

public class HqlDemo {

    private final SessionCreator sessionCreator;

    public HqlDemo(SessionCreator sessionCreator) {
        this.sessionCreator = sessionCreator;
    }

    public Stream<User> getAll() {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session){
            Query<User> query = session.createNamedQuery(User.GET_ALL, User.class);
            List<User> users = query.list();
            transaction.commit();
            return users.stream();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    public List<User> getBetween(Long minId, Long maxId) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session){
            Query<User> query = session.createQuery(
                    "select u from User u where u.id between :minId and :maxId", User.class);
            query.setParameter("minId", minId);
            query.setParameter("maxId", maxId);
            List<User> users = query.list();
            transaction.commit();
            return users;
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    public User getById(Long id) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session){
            Query<User> query = session.createNamedQuery(User.GET_BY_ID, User.class);
            query.setParameter("id", id);
            User user = query.getSingleResult();
            transaction.commit();
            return user;
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    public void setRole(Long id, Role role) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session){
            //ai set example hql
            MutationQuery mutationQuery = session.createMutationQuery("update User u set u.role = :role where u.id = :id");
            mutationQuery.setParameter("id", id);
            mutationQuery.setParameter("role", role);
            mutationQuery.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        HqlDemo hqlDemo = new HqlDemo(new SessionCreator());
        Stream<User> all = hqlDemo.getAll();
        all.forEach(System.out::println);
        line();
        hqlDemo.setRole(2L, Role.ADMIN);
        User alisa = hqlDemo.getById(2L);
        System.out.println(alisa);
        line();
        hqlDemo.setRole(2L, Role.USER);
        List<User> demoBetween = hqlDemo.getBetween(2L, 3L);
        demoBetween.forEach(System.out::println);
    }

    private static void line() {
        System.out.println("=".repeat(20));
    }
}

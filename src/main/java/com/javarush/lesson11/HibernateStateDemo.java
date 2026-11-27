package com.javarush.lesson11;

import com.javarush.khmelov.entity.User;
import com.javarush.lesson09.SessionCreator;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.stat.SessionStatistics;

import static com.javarush.khmelov.entity.Role.GUEST;
import static com.javarush.khmelov.entity.Role.USER;

public class HibernateStateDemo {
    public static void main(String[] args) {
        SessionCreator sessionCreator = new SessionCreator();
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session){
            User user = session.find(User.class, 1L);
            User user2 = session.get(User.class, 2L);
            show(session);
            System.out.println(user);
            user.setLogin("Carl");
            show(session);

            User testUser = User.builder()
                    .login("TestUser")
                    .password("1234")
                    .role(GUEST)
                    .build();
            session.persist(testUser);
            show(session);
            session.flush();
            show(session);
            testUser.setRole(USER);
            show(session);
            session.flush();
            show(session);
            session.remove(testUser);
            show(session);
            session.flush();
            show(session);
            System.out.println(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace(System.err);
        }
    }

    private static void show(Session session) {
        String name = session.toString();
        SessionStatistics statistics = session.getStatistics();
        boolean sessionDirty = session.isDirty();
        String line = "=".repeat(50);
        System.out.printf("""
                name: %s
                statistics: %s
                dirty: %s
                %s
                """, name, statistics, sessionDirty, line);
    }
}

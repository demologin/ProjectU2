package com.javarush.lesson09;

import com.javarush.khmelov.entity.Answer;
import com.javarush.khmelov.entity.Question;
import com.javarush.khmelov.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

public class SessionCreator implements AutoCloseable {
    private final SessionFactory sessionFactory;

    public SessionCreator() {
        sessionFactory = new Configuration()
                .configure()
                .setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy())
                .addAnnotatedClass(User.class)
                .buildSessionFactory();

        // configuration.configure("hibernate.cfg.xml");
        // configuration.configure(new File("path/to/hibernate.cfg.xml"));
        // configuration.configure(SessionCreator.class.getResource("/hibernate.cfg.xml"));
        // configuration.addResource("User.hbm.xml");
        // configuration.addPackage("com.example.model");
        // configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/db");
        // configuration.setProperties(properties);
        // Properties properties = configuration.getProperties();

    }

    public Session getSession() {
        return sessionFactory.openSession();
    }

    @Override
    public void close() throws Exception {
        sessionFactory.close();
    }

    public static void main(String[] args) {
        SessionCreator sessionCreator = new SessionCreator();
        try (sessionCreator) {
            Session session = sessionCreator.getSession();
            Transaction transaction = session.beginTransaction();
            try (session) {
                User user = session.find(User.class, 1L);
                System.out.println(user);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

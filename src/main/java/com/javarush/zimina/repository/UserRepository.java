package com.javarush.zimina.repository;

import com.javarush.zimina.entity.Role;
import com.javarush.zimina.entity.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class UserRepository {

    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public UserRepository() {
        save(new User(null, "Anna", "123", Role.ADMIN));
        save(new User(null, "Ilon", "Qwerty", Role.USER));
        save(new User(null, "Daniel", "321", Role.USER));
    }

    public void save(User user) {
        user.setId(nextId.getAndIncrement());
        users.put(user.getId(), user);
    }

    public User findByLogin(String login) {
        for (User user : users.values()) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }
}
package com.javarush.zimina.repository;

import com.javarush.zimina.entity.Role;
import com.javarush.zimina.entity.User;

public class UserRepository extends BaseRepository<User> {

    public UserRepository() {
        save(new User(null, "Anna", "123", Role.ADMIN));
        save(new User(null, "Ilon", "Qwerty", Role.USER));
        save(new User(null, "Daniel", "321", Role.USER));
    }

    public User findByLogin(String login) {
        for (User user : findAll()) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }
}
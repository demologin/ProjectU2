package com.javarush.sharifullina.repository;

import com.javarush.sharifullina.entity.User;

import java.util.stream.Stream;

public class UserRepository extends BaseRepository<User> {

    public Stream<User> find(User pattern) {
        return map.values().stream()
                .filter(user -> nullOrEquals(pattern.getId(), user.getId()))
                .filter(user -> nullOrEquals(pattern.getLogin(), user.getLogin()))
                .filter(user -> nullOrEquals(pattern.getPassword(), user.getPassword()))
                .filter(user -> nullOrEquals(pattern.getRole(), user.getRole()))
                .filter(user -> nullOrEquals(pattern.getName(), user.getName()))
                .filter(user -> nullOrEquals(pattern.getSurname(), user.getSurname()))
                .filter(user -> nullOrEquals(pattern.getBirthday(), user.getBirthday()));
    }

    private boolean nullOrEquals(Object pattern, Object user) {
        return pattern == null || pattern.equals(user);
    }
}

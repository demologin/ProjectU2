package com.javarush.quest.repository;

import com.javarush.quest.config.annotation.Component;
import com.javarush.quest.entity.User;
import com.javarush.quest.repository.base.BaseRepository;

import java.util.Optional;

@Component
public class UserRepository extends BaseRepository<User, Long> {

    public Optional<User> findByLoginAndPassword(String login, String password) {
        return map.values().stream()
                .filter(user -> user.getLogin().equals(login))
                .filter(user -> user.getPassword().equals(password))
                .findFirst();
    }
}

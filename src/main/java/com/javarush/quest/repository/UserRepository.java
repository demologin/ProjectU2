package com.javarush.quest.repository;

import com.javarush.quest.config.annotation.Component;
import com.javarush.quest.entity.User;
import com.javarush.quest.repository.base.BaseRepository;
import lombok.Getter;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class UserRepository extends BaseRepository<User, Long> {
    @Getter private final AtomicLong id = new AtomicLong();

    public Optional<User> findByLoginAndPassword(String login, String password) {
        return map.values().stream()
                .filter(user -> user.getLogin().equals(login))
                .filter(user -> user.getPassword().equals(password))
                .findFirst();
    }
}

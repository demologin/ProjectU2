package com.javarush.quest.service;

import com.javarush.quest.config.annotation.Component;
import com.javarush.quest.entity.User;
import com.javarush.quest.exception.EntityNotFoundException;
import com.javarush.quest.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUser(String login, String password) {
        return userRepository.findBy(login, password)
                .orElseThrow(() -> EntityNotFoundException.of(User.class));
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public User createUser(String login, String password) {
        User user = User.builder()
                .login(login)
                .password(password)
                .build();
        return userRepository.save(user);
    }
}

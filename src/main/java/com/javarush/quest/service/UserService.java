package com.javarush.quest.service;

import com.javarush.quest.config.annotation.Component;
import com.javarush.quest.entity.User;
import com.javarush.quest.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    public User getUser(String login, String password) {
        return userRepository.findByLoginAndPassword(login, password).orElseThrow();
    }

    public void createUser(User user) {
        userRepository.save(user);
    }
}

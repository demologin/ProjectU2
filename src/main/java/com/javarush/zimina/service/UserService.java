package com.javarush.zimina.service;

import com.javarush.zimina.entity.Role;
import com.javarush.zimina.entity.User;
import com.javarush.zimina.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean register(String login, String password) {
        if(userRepository.findByLogin(login) != null) {
            log.warn("User with login {} already exists", login);
            return false;
        }
        User user = new User(null, login, password, Role.USER);
        userRepository.save(user);
        log.info("User with login {} created", login);
        return true;
    }

    public User login(String login, String password) {
        User user = userRepository.findByLogin(login);
        if (user == null) {
            log.warn("User with login {} not found", login);
            return null;
        }
        if (user.getPassword().equals(password)) {
            log.info("User {} logged in", login);
            return user;
        }
        log.warn("Wrong password for user {}", login);
        return null;
    }
}

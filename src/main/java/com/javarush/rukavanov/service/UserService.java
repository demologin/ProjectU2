package com.javarush.rukavanov.service;

import com.javarush.rukavanov.entity.User;
import com.javarush.rukavanov.repository.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public boolean register(User user) throws IOException {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return false;
        }
        userRepository.save(user);
        return true;
    }

    public void update(User user) throws IOException {
        userRepository.update(user);
    }

    public boolean authenticate(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(u -> u.getPassword().equals(password)).orElse(false);

    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}

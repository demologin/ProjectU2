package com.javarush.sharifullina.service;

import com.javarush.sharifullina.entity.User;
import com.javarush.sharifullina.repository.UserRepository;

import java.util.Collection;
import java.util.Comparator;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Collection<User> getAll() {
        return userRepository.getAll();
    }

    public Collection<User> getSortedLimit(int limit) {
            Collection<User> users = userRepository.getAll();

            return users.stream()
                    .sorted(Comparator.comparingInt(User::getScore).reversed()) // Используем getScoreInt, как в вашей JSP
                    .limit(limit)
                    .toList();
    }

    public User getUser(long id) {
        return userRepository.get(id).orElse(null);
    }

    public User getUser(String login, String password) {
        User userPattern = User.builder().login(login).password(password).build();
        return userRepository.find(userPattern).findAny().orElse(null);
    }

    public boolean isRepeatLogin(String login) {
        User userPattern = User.builder().login(login).build();
        User user = userRepository.find(userPattern).findAny().orElse(null);
        return user != null;
    }

    public Long createUser(User user) {
        return userRepository.create(user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public void updateUser(User user) {
        userRepository.update(user);
    }
}






























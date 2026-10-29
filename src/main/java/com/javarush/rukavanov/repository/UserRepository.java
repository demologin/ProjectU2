package com.javarush.rukavanov.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.javarush.rukavanov.entity.User;
import com.javarush.rukavanov.util.JsonUtil;
import lombok.Setter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.*;


public class UserRepository {
    private final Map<Long, User> users = new HashMap<>();
    private final JsonUtil jsonUtil = new JsonUtil();
    private final Path usersFile = Path.of(
            Objects.requireNonNull(UserRepository.class.getClassLoader()
                            .getResource("users.json"))
                    .getPath()
    );

    @Setter
    private long nextId = 1;

    public UserRepository() throws IOException {
        loadUsers();
    }

    public void save(User user) throws IOException {
        user.setId(nextId++);
        users.put(user.getId(), user);
        saveUsers();
    }

    public void update(User user) throws IOException {
        users.put(user.getId(), user);
        saveUsers();
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }


    public Optional<User> findByUsername(String username) {
        for (User user : users.values()) {
            if (Objects.equals(user.getUsername(), username)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    private void loadUsers() throws IOException {
        try (InputStream inputStream = UserRepository.class.getClassLoader().getResourceAsStream("users.json")) {
            if (inputStream == null) {
                throw new IOException("file not found");
            }
            List<User> userList = jsonUtil.read(inputStream,
                    new TypeReference<List<User>>() {
                    });

            for (User user : userList) {
                users.put(user.getId(), user);
            }

            nextId = users.keySet().stream()
                    .mapToLong(Long::longValue)
                    .max()
                    .orElse(0L) + 1;

        }

    }

    private void saveUsers() throws IOException {
        jsonUtil.write(usersFile, users.values());
    }

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }


}

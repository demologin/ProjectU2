package com.javarush.khmelov.repository;

import com.javarush.khmelov.entity.User;
import com.javarush.lesson09.SessionCreator;

import java.util.stream.Stream;

public class UserRepository extends BaseRepository<User> {

    public UserRepository(SessionCreator sessionCreator) {
        super(User.class, sessionCreator);
    }
}

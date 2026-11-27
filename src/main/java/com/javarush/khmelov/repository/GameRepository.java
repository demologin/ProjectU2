package com.javarush.khmelov.repository;

import com.javarush.khmelov.entity.Game;
import com.javarush.lesson09.SessionCreator;

import java.util.stream.Stream;

public class GameRepository extends BaseRepository<Game> {


    public GameRepository(SessionCreator sessionCreator) {
        super(Game.class, sessionCreator);
    }
}

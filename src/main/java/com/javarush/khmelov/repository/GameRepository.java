package com.javarush.khmelov.repository;

import com.javarush.khmelov.entity.Game;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class GameRepository implements Repository<Game> {
    private final Map<Long, Game> map = new HashMap<>();
    public static final AtomicLong id = new AtomicLong(System.currentTimeMillis());

    @Override
    public Collection<Game> getAll() {
        return map.values();
    }

    @Override
    public Optional<Game> get(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public void create(Game entity) {
        entity.setId(id.incrementAndGet());
        update(entity);
    }

    @Override
    public void update(Game entity) {
        map.put(entity.getId(), entity);
    }

    @Override
    public void delete(Game entity) {

    }
}

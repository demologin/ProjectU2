package com.javarush.zimina.repository;

import com.javarush.zimina.entity.GameResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class GameResultRepository {
    private final Map<Long, GameResult> results = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public void save(GameResult result) {
        result.setId(nextId.getAndIncrement());
        results.put(result.getId(), result);
    }

    public List<GameResult> findAll() {
        return new ArrayList<>(results.values());
    }
}
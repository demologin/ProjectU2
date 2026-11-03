package com.javarush.zimina.repository;

import com.javarush.zimina.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public abstract class BaseRepository<T extends Entity> {

    private final Map<Long, T> storage = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public void save(T entity) {
        entity.setId(nextId.getAndIncrement());
        storage.put(entity.getId(), entity);
    }

    public T findById(Long id) {
        return storage.get(id);
    }

    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    public void delete(Long id) {
        storage.remove(id);
    }
}

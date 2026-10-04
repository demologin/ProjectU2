package com.javarush.quest.repository;

import com.javarush.quest.entity.Entity;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BaseRepository<T extends Entity<ID>, ID> implements Repository<T, ID> {
    protected final Map<ID, T> map = new ConcurrentHashMap<>();

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Collection<T> findAll() {
        return map.values();
    }

    @Override
    public void save(T entity) {
        map.put(entity.getId(), entity);
    }
}

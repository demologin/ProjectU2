package com.javarush.quest.repository.base;

import com.javarush.quest.entity.Entity;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BaseRepository<T extends Entity<ID>, ID> implements Repository<T, ID> {
    protected final Map<ID, T> map = new ConcurrentHashMap<>();

    protected abstract ID generateId();

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Collection<T> findAll() {
        return map.values();
    }

    @Override
    public T save(T entity) {
        if (entity.getId() == null) {
            entity.setId(generateId());
        }
        map.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public void delete(T entity) {
        map.remove(entity.getId());
    }
}

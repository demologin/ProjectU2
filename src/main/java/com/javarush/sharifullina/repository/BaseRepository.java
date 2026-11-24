package com.javarush.sharifullina.repository;

import com.javarush.sharifullina.entity.EntityID;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class BaseRepository<T extends EntityID> implements Repository<T> {

    protected final Map<Long, T> map = new ConcurrentHashMap<>();
    public static final AtomicLong id = new AtomicLong(0L);

    @Override
    public Long create(T t) {
        t.setId(id.incrementAndGet());
        map.put(t.getId(), t);
        return t.getId();
    }

    @Override
    public void update(T t) {
        Long tId = t.getId();
        map.put(tId, t);
    }

    @Override
    public void delete(T t) {
        Long tId = t.getId();
        map.remove(tId);
    }

    @Override
    public Collection<T> getAll() {
        return map.values();
    }

    @Override
    public Optional<T> get(long id) {
        return Optional.ofNullable(map.get(id));
    }
}

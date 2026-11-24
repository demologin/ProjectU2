package com.javarush.sharifullina.repository;

import java.util.Collection;
import java.util.Optional;

public interface Repository<T> {
    Long create(T t);

    void update(T t);

    void delete(T t);

    Collection<T> getAll();

    Optional<T> get(long id);
}

package com.javarush.quest.repository.base;

import java.util.Collection;
import java.util.Optional;

public interface Repository<T, ID> {

    Optional<T> findById(ID id);

    Collection<T> findAll();

    T save(T entity);

    void delete(T entity);
}

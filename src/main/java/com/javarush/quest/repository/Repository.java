package com.javarush.quest.repository;

import java.util.Collection;
import java.util.Optional;

public interface Repository<T, ID> {

    Optional<T> findById(ID id);

    Collection<T> findAll();

    void save(T entity);
}

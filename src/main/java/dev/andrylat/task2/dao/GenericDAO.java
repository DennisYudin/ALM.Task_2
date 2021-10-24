package dev.andrylat.task2.dao;

import org.springframework.data.domain.Pageable;

public interface GenericDAO<T> {

    T getById(long id);

    Iterable<T> findAll(Pageable pageable);

    void save(T t);

    void update(T t);

    void delete(long id);
}


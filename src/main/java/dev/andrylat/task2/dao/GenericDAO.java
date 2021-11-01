package dev.andrylat.task2.dao;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GenericDAO<T> {

    T getById(long id);

    List<T> findAll(Pageable pageable);

    void save(T t);

    void update(T t);

    void delete(long id);
}


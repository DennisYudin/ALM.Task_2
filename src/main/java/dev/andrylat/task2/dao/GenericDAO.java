package dev.andrylat.task2.dao;

public interface GenericDAO<T> {

    T getById(long id);

    Iterable<T> findAll();

    void save(T t);

    void update(T t);

    void delete(long id);
}


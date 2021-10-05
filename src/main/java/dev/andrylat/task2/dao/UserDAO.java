package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.User;

import java.util.List;

public interface UserDAO extends GenericDAO<User>{

    User getById(long id);

    List<User> findAll();

    void save(User user);

    void update(User user);

    void delete(long id);
}

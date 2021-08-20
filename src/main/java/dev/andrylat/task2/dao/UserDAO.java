package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.User;

import java.util.List;

public interface UserDAO {

    List<User> findAll();
    List<User> findByName(String name);
    void insert(User user);
    void update(User user);
    void delete(long userId);
}

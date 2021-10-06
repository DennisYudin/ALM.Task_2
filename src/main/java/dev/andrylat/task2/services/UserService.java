package dev.andrylat.task2.services;

import dev.andrylat.task2.entities.User;

import java.util.List;

public interface UserService {

    User getById(long id);

    List<User> findAll();

    void save(User user);

    void update(User user);

    void delete(long id);
}

package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.User;

import java.util.List;

public interface UserDAO {

    User getUser(long id);
    List<User> getUsers();
    void saveUser(User user);
    void updateUser(User user);
    void deleteUser(long id);
}

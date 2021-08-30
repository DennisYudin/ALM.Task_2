package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.User;

import java.util.List;

public interface UserDAO {

    User getUserById(long theId);
    List<User> getUsers();
    void saveUser(User theUser);
    void updateUser(User theUser);
    void deleteUser(long theId);
}

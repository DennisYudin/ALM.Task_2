package dev.andrylat.task2.services;

import dev.andrylat.task2.entities.User;

import java.util.List;

public interface UserService extends GenericService<User> {

    List<String> getAllEventsByUserId(long id);

    void addNewEvent(long firstId, long secondId);

    void removeEvent(long firstId, long secondId);
}

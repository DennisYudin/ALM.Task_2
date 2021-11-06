package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.User;

import java.util.List;

public interface UserDAO extends GenericDAO<User> {

    List<String> getAllEventsByUserId(long id);

    void addNewEvent(long userId, long eventId);

    void removeEvent(long userId, long eventId);
}

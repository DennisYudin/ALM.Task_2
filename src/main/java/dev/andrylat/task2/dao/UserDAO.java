package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.Event;
import dev.andrylat.task2.entities.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserDAO extends GenericDAO<User> {

    List<Event> getAllEventsByUserId(long id, Pageable pageable);

    void assignEvent(long userId, long eventId);

    void removeEvent(long userId, long eventId);
}

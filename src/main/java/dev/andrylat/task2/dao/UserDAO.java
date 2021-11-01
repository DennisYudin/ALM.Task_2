package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.Event;
import dev.andrylat.task2.entities.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserDAO extends GenericDAO<User>{

    User getById(long id);

    List<User> findAll(Pageable pageable);

    void save(User user);

    void update(User user);

    void delete(long id);

    List<String> getAllEventsByUserId(long id);

    void addNewEvent(long userId, long eventId);

    void removeEvent(long userId, long eventId);
}

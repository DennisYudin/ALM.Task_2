package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserDAO extends GenericDAO<User>{

    User getById(long id);

    List<User> findAll(Pageable pageable);

    void save(User user);

    void update(User user);

    void delete(long id);

    List<Long> getAllEventsByUserId(long id);

    void addNewEvent(long firstId, long secondId);

    void removeEvent(long firstId, long secondId);
}

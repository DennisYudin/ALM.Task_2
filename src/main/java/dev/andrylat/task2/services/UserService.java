package dev.andrylat.task2.services;

import dev.andrylat.task2.entities.Event;
import dev.andrylat.task2.entities.User;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface UserService extends GenericService<User> {

    List<Event> getAllEventsByUserId(long id, Pageable pageable);

    void assignEvent(long firstId, long secondId);

    void removeEvent(long firstId, long secondId);
}

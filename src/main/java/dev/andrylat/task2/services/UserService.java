package dev.andrylat.task2.services;

import dev.andrylat.task2.entities.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    User getUserById(long id);

    List<User> findAllUsers(Pageable pageable);

    void saveUser(User user);

    void deleteUserById(long id);

    List<String> getAllEventsByUserId(long id);

    void addNewEvent(long firstId, long secondId);

    void removeEvent(long firstId, long secondId);
}

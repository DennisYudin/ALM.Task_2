package dev.andrylat.task2.dto.mapper;

import dev.andrylat.task2.dto.UserDTO;
import dev.andrylat.task2.entities.Event;
import dev.andrylat.task2.entities.Ticket;
import dev.andrylat.task2.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public UserDTO convertToDTO(User user, List<Event> events) {

        return new UserDTO.Builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .events(events)
                .build();
    }
}

package dev.andrylat.task2.services;

import dev.andrylat.task2.dto.EventDTO;
import dev.andrylat.task2.entities.Event;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventService {

    Event getEventById(long id);

    List<Event> findAllEvents(Pageable pageable);

    void saveEvent(Event event);

    void deleteEventById(long id);

    List<Long> getAllCategoriesByEventId(long id);

    void addNewCategory(long firstId, long secondId);

    void removeCategory(long firstId, long secondId);

    EventDTO getEventWithDetails(long id);
}

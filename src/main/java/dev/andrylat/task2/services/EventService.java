package dev.andrylat.task2.services;

import dev.andrylat.task2.dto.EventDTO;
import dev.andrylat.task2.entities.Event;

import java.util.List;

public interface EventService extends GenericService<Event> {

    List<String> getAllCategoriesByEventId(long id);

    void addNewCategory(long eventId, long categoryId);

    void removeCategory(long eventId, long categoryId);

    EventDTO getEventWithDetails(long id);
}

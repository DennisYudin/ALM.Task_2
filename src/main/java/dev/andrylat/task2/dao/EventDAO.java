package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.Event;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventDAO extends GenericDAO<Event>  {

    Event getById(long id);

    List<Event> findAll(Pageable pageable);

    void save(Event event);

    void update(Event event);

    void delete(long id);

    List<String> getAllCategoriesByEventId(long id);

    void addNewCategory(long eventId, long categoryId);

    void removeCategory(long eventId, long categoryId);
}


package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.Event;

import java.util.List;

public interface EventDAO extends GenericDAO<Event> {

    List<String> getAllCategoriesByEventId(long id);

    void assignCategory(long eventId, long categoryId);

    void removeCategory(long eventId, long categoryId);
}


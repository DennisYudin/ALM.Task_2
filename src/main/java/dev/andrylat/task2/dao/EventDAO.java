package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.Event;

import java.util.List;

public interface EventDAO {

    Event getEvent(long id);
    List<Event> findAll();
    void save(Event event);
    void update(Event event);
    void delete(long id);
}



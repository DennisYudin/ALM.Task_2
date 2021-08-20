package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.Event;

import java.util.List;

public interface EventDAO {

    List<Event> findAll();
    List<Event> findByTitle(String title);
    void insert(Event event);
    void update(Event event);
    void delete(long eventId);
}

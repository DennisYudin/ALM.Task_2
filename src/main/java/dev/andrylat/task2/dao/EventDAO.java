package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.Event;

import java.util.List;

public interface EventDAO {

    Event getEvent(long id);
    List<Event> getEvents();

    void saveEvent(Event event);
    void updateEvent(Event event);
    void deleteEvent(long id);
}



package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.Event;

import java.util.List;

public interface EventDAO {

    Event getEventById(long theId);
    List<Event> getEvents();

    void saveEvent(Event theEvent);
    void updateEvent(Event theEvent);
    void deleteEvent(long theId);

    List<Event> sortByName();
    List<Event> sortByPrice();
    List<Event> sortByDate();
}

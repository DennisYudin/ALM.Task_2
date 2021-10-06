package dev.andrylat.task2.services;

import dev.andrylat.task2.entities.Category;
import dev.andrylat.task2.entities.Event;
import dev.andrylat.task2.entities.Location;

import java.util.List;

public interface EventService {

    Category getCategoryById(long id);

    List<Category> findAllCategories();

    void saveCategory(Category category);

    void updateCategory(Category category);

    void deleteCategoryById(long id);

    Event getEventById(long id);

    List<Event> findAllEvents();

    void saveEvent(Event event);

    void updateEvent(Event event);

    void deleteEventById(long id);

    Location getLocationById(long id);

    List<Location> findAllLocations();

    void saveLocation(Location location);

    void updateLocation(Location location);

    void deleteLocationById(long id);
}

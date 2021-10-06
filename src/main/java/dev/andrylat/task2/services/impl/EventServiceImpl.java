package dev.andrylat.task2.services.impl;

import dev.andrylat.task2.dao.CategoryDAO;
import dev.andrylat.task2.dao.EventDAO;
import dev.andrylat.task2.dao.LocationDAO;
import dev.andrylat.task2.entities.Category;
import dev.andrylat.task2.entities.Event;
import dev.andrylat.task2.entities.Location;
import dev.andrylat.task2.exceptions.ServiceException;
import dev.andrylat.task2.services.EventService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private static final Logger logger = Logger.getLogger(EventServiceImpl.class);

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private LocationDAO locationDAO;

    @Override
    public Category getCategoryById(long id) {
        logger.trace("Call method getCategoryById() with id = " + id);

        Category category;
        try {
            category = categoryDAO.getById(id);
        } catch (Exception ex) {
            logger.error("Could not get category by id = " + id, ex);
            throw new ServiceException("Could not get category by id = " + id, ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Category is" + category);
        }
        return category;
    }

    @Override
    public List<Category> findAllCategories() {
        logger.trace("Call method findAllCategories()");

        List<Category> categories;
        try {
            categories = categoryDAO.findAll();
        } catch (Exception ex) {
            logger.error("Could not get categories", ex);
            throw new ServiceException("Could not get categories", ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Categories are " + categories);
        }
        return categories;
    }

    @Override
    public void saveCategory(Category category) {
        logger.trace("Call method saveCategory()");

        try {
            categoryDAO.save(category);
        } catch (Exception ex) {
            logger.error("Could not save category", ex);
            throw new ServiceException("Could not save category", ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(category + "is added in DB");
        }
    }

    @Override
    public void updateCategory(Category category) {
        logger.trace("Call method updateCategory()");

        try {
            categoryDAO.update(category);
        } catch (Exception ex) {
            logger.error("Could not update category", ex);
            throw new ServiceException("Could not update category", ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(category + "is updated in DB");
        }
    }

    @Override
    public void deleteCategoryById(long id) {
        logger.trace("Call method deleteCategoryById() with id = " + id);

        try {
            categoryDAO.delete(id);
        } catch (Exception ex) {
            logger.error("Could not delete category", ex);
            throw new ServiceException("Could not delete category", ex);
        }
        logger.debug("Category is deleted in DB");
    }

    @Override
    public Event getEventById(long id) {
        logger.trace("Call method getEventById() with id = " + id);

        Event event;
        try {
            event = eventDAO.getById(id);
        } catch (Exception ex) {
            logger.error("Could not get event by id = " + id, ex);
            throw new ServiceException("Could not get event by id = " + id, ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Event is" + event);
        }
        return event;
    }

    @Override
    public List<Event> findAllEvents() {
        logger.trace("Call method findAllEvents()");

        List<Event> events;
        try {
            events = eventDAO.findAll();
        } catch (Exception ex) {
            logger.error("Could not get events", ex);
            throw new ServiceException("Could not get events", ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Events are " + events);
        }
        return events;
    }

    @Override
    public void saveEvent(Event event) {
        logger.trace("Call method saveEvent()");

        try {
            eventDAO.save(event);
        } catch (Exception ex) {
            logger.error("Could not save event", ex);
            throw new ServiceException("Could not save event", ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(event + "is added in DB");
        }
    }

    @Override
    public void updateEvent(Event event) {
        logger.trace("Call method updateEvent()");

        try {
            eventDAO.update(event);
        } catch (Exception ex) {
            logger.error("Could not update event", ex);
            throw new ServiceException("Could not update event", ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(event + "is updated in DB");
        }
    }

    @Override
    public void deleteEventById(long id) {
        logger.trace("Call method deleteEventById() with id = " + id);

        try {
            eventDAO.delete(id);
        } catch (Exception ex) {
            logger.error("Could not delete category", ex);
            throw new ServiceException("Could not delete category", ex);
        }
        logger.debug("Event is deleted in DB");
    }

    @Override
    public Location getLocationById(long id) {
        logger.trace("Call method getLocationById() with id = " + id);

        Location location;
        try {
            location = locationDAO.getById(id);
        } catch (Exception ex) {
            logger.error("Could not get location by id = " + id, ex);
            throw new ServiceException("Could not get location by id = " + id, ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Location is" + location);
        }
        return location;
    }

    @Override
    public List<Location> findAllLocations() {
        logger.trace("Call method findAllLocations()");

        List<Location> locations;
        try {
            locations = locationDAO.findAll();
        } catch (Exception ex) {
            logger.error("Could not get locations", ex);
            throw new ServiceException("Could not get locations", ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Locations are " + locations);
        }
        return locations;
    }

    @Override
    public void saveLocation(Location location) {
        logger.trace("Call method saveLocation()");

        try {
            locationDAO.save(location);
        } catch (Exception ex) {
            logger.error("Could not save location", ex);
            throw new ServiceException("Could not save location", ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(location + "is added in DB");
        }
    }

    @Override
    public void updateLocation(Location location) {
        logger.trace("Call method updateLocation()");

        try {
            locationDAO.update(location);
        } catch (Exception ex) {
            logger.error("Could not update location", ex);
            throw new ServiceException("Could not update location", ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(location + "is updated in DB");
        }
    }

    @Override
    public void deleteLocationById(long id) {
        logger.trace("Call method deleteLocationById() with id = " + id);

        try {
            locationDAO.delete(id);
        } catch (Exception ex) {
            logger.error("Could not delete location", ex);
            throw new ServiceException("Could not delete location", ex);
        }
        logger.debug("Location is deleted in DB");
    }
}


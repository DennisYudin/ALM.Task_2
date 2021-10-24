package dev.andrylat.task2.services.impl;

import dev.andrylat.task2.dao.CategoryDAO;
import dev.andrylat.task2.dao.EventDAO;
import dev.andrylat.task2.dao.LocationDAO;
import dev.andrylat.task2.dto.EventDTO;
import dev.andrylat.task2.dto.mapper.EventMapper;
import dev.andrylat.task2.entities.Event;
import dev.andrylat.task2.entities.Location;
import dev.andrylat.task2.exceptions.ServiceException;
import dev.andrylat.task2.services.EventService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private static final Logger logger = Logger.getLogger(EventServiceImpl.class);

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private LocationDAO locationDAO;

    @Autowired
    private EventMapper eventMapper;

    @Override
    public Event getEventById(long id) {
        logger.debug("Call method getEventById() with id = " + id);

        Event event;
        try {
            event = eventDAO.getById(id);
        } catch (Exception ex) {
            logger.error("Could not get event by id = " + id, ex);
            throw new ServiceException("Could not get event by id = " + id, ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Event is" + event.toString());
        }
        return event;
    }

    @Override
    public List<Event> findAllEvents(Pageable pageable) {
        logger.debug("Call method findAllEvents()");

        List<Event> events;
        try {
            events = eventDAO.findAll(pageable);
        } catch (Exception ex) {
            logger.error("Could not get events", ex);
            throw new ServiceException("Could not get events", ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Events are " + events.toString());
        }
        return events;
    }

    @Override
    public void saveEvent(Event event) {

        Event resultQuery = getEventById(event.getId());

        if (resultQuery == null) {
            saveNewEvent(event);
        } else {
            updateEvent(event);
        }
    }

    private void saveNewEvent(Event event) {
        logger.debug("Call method saveNewEvent() for event with id = " + event.getId());

        try {
            eventDAO.save(event);
        } catch (Exception ex) {
            logger.error("Could not save event with id = " + event.getId(), ex);
            throw new ServiceException("Could not save event with id = " + event.getId(), ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(event.toString() + "is added in DB");
        }
    }

    private void updateEvent(Event event) {
        logger.debug("Call method updateEvent() for event with id = " + event.getId());

        try {
            eventDAO.update(event);
        } catch (Exception ex) {
            logger.error("Could not update event with id = " + event.getId(), ex);
            throw new ServiceException("Could not update event with id = " + event.getId(), ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(event.toString() + "is updated in DB");
        }
    }

    @Override
    public void deleteEventById(long id) {
        logger.debug("Call method deleteEventById() with event id = " + id);

        try {
            eventDAO.delete(id);
        } catch (Exception ex) {
            logger.error("Could not delete event with id = " + id, ex);
            throw new ServiceException("Could not delete event with id = " + id, ex);
        }
        logger.debug("Event with id = " + id + " is deleted in DB");
    }

    @Override
    public List<Long> getAllCategoriesByEventId(long id) {
        logger.debug("Call method getAllCategoriesByEventId() with id = " + id);

        List<Long> categories;
        try {
            categories = eventDAO.getAllCategoriesByEventId(id);
        } catch (Exception ex) {
            logger.error("Could not get categories", ex);
            throw new ServiceException("Could not get categories", ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Categories are " + categories.toString());
        }
        return categories;
    }

    @Override
    public void addNewCategory(long firstId, long secondId) {
        logger.debug("Call method addNewCategory() for " +
                "event id = " + firstId + " and category id = " + secondId);

        try {
            eventDAO.addNewCategory(firstId, secondId);
        } catch (Exception ex) {
            logger.error("Could not add category with " +
                    "event id = " + firstId + " and category id = " + secondId, ex);
            throw new ServiceException("Could not add category with " +
                    "event id = " + firstId + " and category id = " + secondId, ex);
        }
        logger.debug("Category with id = " + secondId + "is added in DB");
    }

    @Override
    public void removeCategory(long firstId, long secondId) {
        logger.debug("Call method removeCategory() for " +
                "event id = " + firstId + " and category id = " + secondId);

        try {
            eventDAO.removeCategory(firstId, secondId);
        } catch (Exception ex) {
            logger.error("Could not delete category with id = " + secondId, ex);
            throw new ServiceException("Could not delete category with id = " + secondId, ex);
        }
        logger.debug("Category with id = " + secondId + " is deleted in DB");
    }

    @Override
    public EventDTO getEventWithDetails(long id) {
        logger.debug("Call method getEventWithDetails() with id = " + id);

        Event event = getEventById(id);

        List<Long> categories = getAllCategoriesByEventId(id);

        List<String> categoryNames = getCategoryNames(categories, categoryDAO);

        Location location = getLocationById(event.getLocationId());

        EventDTO eventDTO = eventMapper.convertToDTO(event, categoryNames, location);

        if (logger.isDebugEnabled()) {
            logger.debug("EventDTO is " + eventDTO.toString());
        }
        return eventDTO;
    }

    private List<String> getCategoryNames(List<Long> input, CategoryDAO dao) {

        List<String> result;
        try {
            result = input.stream()
                    .map(id -> dao.getById(id).getTitle())
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            logger.error("Could not get category", ex);
            throw new ServiceException("Could not get category", ex);
        }
        return result;
    }

    private Location getLocationById(long id) {
        logger.debug("Call method getLocationById() with id = " + id);

        Location location;
        try {
            location = locationDAO.getById(id);
        } catch (Exception ex) {
            logger.error("Could not get location by id = " + id, ex);
            throw new ServiceException("Could not get location by id = " + id, ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Location is" + location.toString());
        }
        return location;
    }
}


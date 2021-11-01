package dev.andrylat.task2.services.impl;

import dev.andrylat.task2.dao.CategoryDAO;
import dev.andrylat.task2.dao.EventDAO;
import dev.andrylat.task2.dao.LocationDAO;
import dev.andrylat.task2.dto.EventDTO;
import dev.andrylat.task2.dto.mapper.EventMapper;
import dev.andrylat.task2.entities.Event;
import dev.andrylat.task2.entities.Location;
import dev.andrylat.task2.exceptions.DAOException;
import dev.andrylat.task2.exceptions.DataNotFoundException;
import dev.andrylat.task2.exceptions.ServiceException;
import dev.andrylat.task2.services.EventService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

        validateId(id);

        Event event;
        try {
            event = eventDAO.getById(id);
        } catch (DataNotFoundException ex) {
            logger.error("There is no such event with id = " + id);
            throw new ServiceException("There is no such event with id = " + id, ex);
        } catch (DAOException ex) {
            logger.error("Something went wrong when trying to call the method getEventById()");
            throw new ServiceException("Something went wrong when trying to call the method getEventById()", ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Event is " + event);
        }
        return event;
    }

    private void validateId(long id) {
        logger.debug("Call method validateId() with id = " + id);
        if (id <= 0) {
            logger.error("id can not be less or equals zero");
            throw new ServiceException("id can not be less or equals zero");
        }
    }


    @Override
    public List<Event> findAllEvents(Pageable pageable) {
        logger.debug("Call method findAllEvents()");

        List<Event> events;
        try {
            events = eventDAO.findAll(pageable);
        } catch (DAOException ex) {
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
        logger.debug("Call method saveEvent() for event with id = " + event.getId());

        validate(event);
    }

    private void validate(Event event) {
        logger.debug("Call method validate() for event with id = " + event.getId());

        validateId(event.getId());

        try {
            eventDAO.getById(event.getId());

            updateEvent(event);
        } catch (DataNotFoundException ex) {
            saveNewEvent(event);
        } catch (DAOException ex) {
            throw new ServiceException("Something went wrong when trying to call the method saveEvent()", ex);
        }
    }

    private void saveNewEvent(Event event) {
        logger.debug("Call method saveNewEvent() for event with id = " + event.getId());

        try {
            eventDAO.save(event);
        } catch (DAOException ex) {
            logger.error("Could not save event with id = " + event.getId(), ex);
            throw new ServiceException("Could not save event with id = " + event.getId(), ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(event + " is added in DB");
        }
    }

    private void updateEvent(Event event) {
        logger.debug("Call method updateEvent() for event with id = " + event.getId());

        try {
            eventDAO.update(event);
        } catch (DAOException ex) {
            logger.error("Could not update event with id = " + event.getId(), ex);
            throw new ServiceException("Could not update event with id = " + event.getId(), ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(event + " is updated in DB");
        }
    }

    @Override
    public void deleteEventById(long id) {
        logger.debug("Call method deleteEventById() with event id = " + id);

        validateId(id);

        try {
            eventDAO.delete(id);
        } catch (DAOException ex) {
            logger.error("Could not delete event with id = " + id, ex);
            throw new ServiceException("Could not delete event with id = " + id, ex);
        }
        logger.debug("Event with id = " + id + " is deleted in DB");
    }

    @Override
    public List<String> getAllCategoriesByEventId(long id) {
        logger.debug("Call method getAllCategoriesByEventId() with id = " + id);

        validateId(id);

        List<String> categories;
        try {
            categories = eventDAO.getAllCategoriesByEventId(id);
        } catch (DAOException ex) {
            logger.error("Could not get categories", ex);
            throw new ServiceException("Could not get categories", ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Categories are " + categories);
        }
        return categories;
    }

    @Override
    public void addNewCategory(long eventId, long categoryId) {
        logger.debug("Call method addNewCategory() for " +
                "event id = " + eventId + " and category id = " + categoryId);

        try {
            eventDAO.addNewCategory(eventId, categoryId);
        } catch (DAOException ex) {
            logger.error("Could not add category with " +
                    "event id = " + eventId + " and category id = " + categoryId, ex);
            throw new ServiceException("Could not add category with " +
                    "event id = " + eventId + " and category id = " + categoryId, ex);
        }
        logger.debug("Category with id = " + categoryId + " is added in DB");
    }

    @Override
    public void removeCategory(long eventId, long categoryId) {
        logger.debug("Call method removeCategory() for " +
                "event id = " + eventId + " and category id = " + categoryId);

        try {
            eventDAO.removeCategory(eventId, categoryId);
        } catch (DAOException ex) {
            logger.error("Could not delete category with id = " + categoryId, ex);
            throw new ServiceException("Could not delete category with id = " + categoryId, ex);
        }
        logger.debug("Category with id = " + categoryId + " is deleted in DB");
    }

    @Override
    public EventDTO getEventWithDetails(long id) {
        logger.debug("Call method getEventWithDetails() with id = " + id);

        Event event = getEventById(id);

        List<String> categoryNames = getAllCategoriesByEventId(id);

        Location location = getLocationById(event.getLocationId());

        EventDTO eventDTO = eventMapper.convertToDTO(event, categoryNames, location);

        if (logger.isDebugEnabled()) {
            logger.debug("EventDTO is " + eventDTO);
        }
        return eventDTO;
    }

    private Location getLocationById(long id) {
        logger.debug("Call method getLocationById() with id = " + id);

        Location location;
        try {
            location = locationDAO.getById(id);
        } catch (DataNotFoundException ex) {
            logger.error("There is no such location with id = " + id);
            throw new ServiceException("There is no such location with id = " + id, ex);
        } catch (DAOException ex) {
            logger.error("Something went wrong when trying to call the method getLocationById()");
            throw new ServiceException("Something went wrong when trying to call the method getLocationById()", ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Location is " + location);
        }
        return location;
    }
}


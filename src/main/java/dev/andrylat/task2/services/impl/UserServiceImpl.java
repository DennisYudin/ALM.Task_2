package dev.andrylat.task2.services.impl;

import dev.andrylat.task2.dao.EventDAO;
import dev.andrylat.task2.dao.TicketDAO;
import dev.andrylat.task2.dao.UserDAO;
import dev.andrylat.task2.dto.UserDTO;
import dev.andrylat.task2.dto.mapper.UserMapper;
import dev.andrylat.task2.entities.Event;
import dev.andrylat.task2.entities.User;
import dev.andrylat.task2.exceptions.ServiceException;
import dev.andrylat.task2.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private TicketDAO ticketDAO;

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(long id) {
        logger.debug("Call method getUserById() with id = " + id);

        User user;
        try {
            user = userDAO.getById(id);
        } catch (Exception ex) {
            logger.error("Could not get user by id = " + id, ex);
            throw new ServiceException("Could not get user by id = " + id, ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("User is " + user.toString());
        }
        return user;
    }

    @Override
    public List<User> findAllUsers(Pageable pageable) {
        logger.debug("Call method findAll()");

        List<User> users;
        try {
            users = userDAO.findAll(pageable);
        } catch (Exception ex) {
            logger.error("Could not get users", ex);
            throw new ServiceException("Could not get users", ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Users are " + users.toString());
        }
        return users;
    }

    @Override
    public void saveUser(User user) {

        User resultQuery = getUserById(user.getId());

        if (resultQuery == null) {
            saveNewUser(user);
        } else {
            updateUser(user);
        }
    }

    private void saveNewUser(User user) {
        logger.debug("Call method saveNewUser() for user with id = " + user.getId());

        try {
            userDAO.save(user);
        } catch (Exception ex) {
            logger.error("Could not save user with id = " + user.getId(), ex);
            throw new ServiceException("Could not save user with id = " + user.getId(), ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(user.toString() + "is added in DB");
        }
    }

    private void updateUser(User user) {
        logger.debug("Call method updateUser() for user with id = " + user.getId());

        try {
            userDAO.update(user);
        } catch (Exception ex) {
            logger.error("Could not update user with id = " + user.getId(), ex);
            throw new ServiceException("Could not update user with id = " + user.getId(), ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(user.toString() + "is updated in DB");
        }
    }

    @Override
    public void deleteUserById(long id) {
        logger.debug("Call method deleteUserById() with id = " + id);

        try {
            userDAO.delete(id);
        } catch (Exception ex) {
            logger.error("Could not delete user with id = " + id, ex);
            throw new ServiceException("Could not delete user with id = " + id, ex);
        }
        logger.debug("User with id = " + id + " is deleted in DB");
    }

    @Override
    public List<Long> getAllEventsByUserId(long id) {
        logger.debug("Call method getAllEventsByUserId() with id = " + id);

        List<Long> events;
        try {
            events = userDAO.getAllEventsByUserId(id);
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
    public void addNewEvent(long firstId, long secondId) {
        logger.debug("Call method addNewEvent() for " +
                "user id = " + firstId + " and event id = " + secondId);

        try {
            userDAO.addNewEvent(firstId, secondId);
        } catch (Exception ex) {
            logger.error("Could not add event with " +
                    "user id = " + firstId + " and event id = " + secondId, ex);
            throw new ServiceException("Could not add event with " +
                    "user id = " + firstId + " and event id = " + secondId, ex);
        }
        logger.debug("Event with id = " + secondId + "is added in DB");
    }

    @Override
    public void removeEvent(long firstId, long secondId) {
        logger.debug("Call method removeEvent() for " +
                "user id = " + firstId + " and event id = " + secondId);

        try {
            userDAO.removeEvent(firstId, secondId);
        } catch (Exception ex) {
            logger.error("Could not delete event with id = " + secondId, ex);
            throw new ServiceException("Could not delete event with id = " + secondId, ex);
        }
        logger.debug("Event with id = " + secondId + " is deleted in DB");
    }

    @Override
    public UserDTO getUserWithDetails(long id) {
        logger.debug("Call method getUserWithDetails() with id = " + id);

        User user = getUserById(id);

        List<Long> eventIDs = getAllEventsByUserId(id);

        List<Event> events = getEvents(eventIDs, eventDAO);

        UserDTO userDTO = userMapper.convertToDTO(user, events);

        return userDTO;
    }

    private List<Event> getEvents(List<Long> input, EventDAO dao) {

        List<Event> result;
        try {
            result = input.stream()
                    .map(id -> dao.getById(id))
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            logger.error("Could not get event", ex);
            throw new ServiceException("Could not get event", ex);
        }
        return result;
    }
}


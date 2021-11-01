package dev.andrylat.task2.services.impl;

import dev.andrylat.task2.dao.EventDAO;
import dev.andrylat.task2.dao.UserDAO;
import dev.andrylat.task2.entities.User;
import dev.andrylat.task2.exceptions.DAOException;
import dev.andrylat.task2.exceptions.DataNotFoundException;
import dev.andrylat.task2.exceptions.ServiceException;
import dev.andrylat.task2.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private EventDAO eventDAO;

    @Override
    public User getUserById(long id) {
        logger.debug("Call method getUserById() with id = " + id);

        validateId(id);

        User user;
        try {
            user = userDAO.getById(id);
        } catch (DataNotFoundException ex) {
            logger.error("There is no such event with id = " + id);
            throw new ServiceException("There is no such event with id = " + id, ex);
        } catch (DAOException ex) {
            logger.error("Something went wrong when trying to call the method getEventById()");
            throw new ServiceException("Something went wrong when trying to call the method getEventById()", ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("User is " + user);
        }
        return user;
    }

    private void validateId(long id) {
        logger.debug("Call method validateId() with id = " + id);
        if (id <= 0) {
            logger.error("id can not be less or equals zero");
            throw new ServiceException("id can not be less or equals zero");
        }
    }

    @Override
    public List<User> findAllUsers(Pageable pageable) {
        logger.debug("Call method findAll()");

        List<User> users;
        try {
            users = userDAO.findAll(pageable);
        } catch (DAOException ex) {
            logger.error("Could not get users", ex);
            throw new ServiceException("Could not get users", ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Users are " + users);
        }
        return users;
    }

    @Override
    public void saveUser(User user) {
        logger.debug("Call method saveUser() for user with id = " + user.getId());

        validate(user);
    }

    private void validate(User user) {
        logger.debug("Call method validate() for user with id = " + user.getId());

        validateId(user.getId());

        try {
            userDAO.getById(user.getId());

            updateUser(user);
        } catch(DataNotFoundException ex) {
            saveNewUser(user);
        } catch (DAOException ex) {
            throw new ServiceException("Something went wrong when trying to call the method saveUser()", ex);
        }
    }

    private void saveNewUser(User user) {
        logger.debug("Call method saveNewUser() for user with id = " + user.getId());

        try {
            userDAO.save(user);
        } catch (DAOException ex) {
            logger.error("Could not save user with id = " + user.getId(), ex);
            throw new ServiceException("Could not save user with id = " + user.getId(), ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(user + "is added in DB");
        }
    }

    private void updateUser(User user) {
        logger.debug("Call method updateUser() for user with id = " + user.getId());

        try {
            userDAO.update(user);
        } catch (DAOException ex) {
            logger.error("Could not update user with id = " + user.getId(), ex);
            throw new ServiceException("Could not update user with id = " + user.getId(), ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(user + "is updated in DB");
        }
    }

    @Override
    public void deleteUserById(long id) {
        logger.debug("Call method deleteUserById() with id = " + id);

        validateId(id);

        try {
            userDAO.delete(id);
        } catch (DAOException ex) {
            logger.error("Could not delete user with id = " + id, ex);
            throw new ServiceException("Could not delete user with id = " + id, ex);
        }
        logger.debug("User with id = " + id + " is deleted in DB");
    }

    @Override
    public List<String> getAllEventsByUserId(long id) {
        logger.debug("Call method getAllEventsByUserId() with id = " + id);

        validateId(id);

        List<String> events;
        try {
            events = userDAO.getAllEventsByUserId(id);
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
    public void addNewEvent(long userId, long eventId) {
        logger.debug("Call method addNewEvent() for " +
                "user id = " + userId + " and event id = " + eventId);

        try {
            userDAO.addNewEvent(userId, eventId);
        } catch (DAOException ex) {
            logger.error("Could not add event with " +
                    "user id = " + userId + " and event id = " + eventId, ex);
            throw new ServiceException("Could not add event with " +
                    "user id = " + userId + " and event id = " + eventId, ex);
        }
        logger.debug("Event with id = " + eventId + " is added in DB");
    }

    @Override
    public void removeEvent(long userId, long eventId) {
        logger.debug("Call method removeEvent() for " +
                "user id = " + userId + " and event id = " + eventId);

        try {
            userDAO.removeEvent(userId, eventId);
        } catch (DAOException ex) {
            logger.error("Could not delete event with id = " + eventId, ex);
            throw new ServiceException("Could not delete event with id = " + eventId, ex);
        }
        logger.debug("Event with id = " + eventId + " is deleted in DB");
    }
}


package dev.andrylat.task2.services.impl;

import dev.andrylat.task2.dao.UserDAO;
import dev.andrylat.task2.entities.User;
import dev.andrylat.task2.exceptions.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceImpl implements UserDAO {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDAO userDAO;

    @Override
    public User getById(long id) {
        logger.trace("Call method getById() with id = " + id);

        User user;
        try {
            user = userDAO.getById(id);
        } catch (Exception ex) {
            logger.error("Could not get user by id = " + id, ex);
            throw new ServiceException("Could not get user by id = " + id, ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("User is" + user);
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        logger.trace("Call method findAll()");

        List<User> users;
        try {
            users = userDAO.findAll();
        } catch (Exception ex) {
            logger.error("Could not get users", ex);
            throw new ServiceException("Could not get users", ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Users are " + users);
        }
        return users;
    }

    @Override
    public void save(User user) {
        logger.trace("Call method save()");

        try {
            userDAO.save(user);
        } catch (Exception ex) {
            logger.error("Could not save user", ex);
            throw new ServiceException("Could not save user", ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(user + "is added in DB");
        }
    }

    @Override
    public void update(User user) {
        logger.trace("Call method update()");

        try {
            userDAO.update(user);
        } catch (Exception ex) {
            logger.error("Could not update user", ex);
            throw new ServiceException("Could not update user", ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(user + "is updated in DB");
        }
    }

    @Override
    public void delete(long id) {
        logger.trace("Call method delete() with id = " + id);

        try {
            userDAO.delete(id);
        } catch (Exception ex) {
            logger.error("Could not delete user", ex);
            throw new ServiceException("Could not delete user", ex);
        }
        logger.debug("User is deleted in DB");
    }
}


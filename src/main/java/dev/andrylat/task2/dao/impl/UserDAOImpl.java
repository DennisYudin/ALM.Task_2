package dev.andrylat.task2.dao.impl;

import dev.andrylat.task2.dao.UserDAO;
import dev.andrylat.task2.entities.User;
import dev.andrylat.task2.exceptions.DAOException;
import dev.andrylat.task2.exceptions.DataNotFoundException;
import dev.andrylat.task2.mappers.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO {
    private static final String SQL_SELECT_USER_BY_ID = "SELECT * FROM users WHERE user_id = ?";
    private static final String SQL_SELECT_ALL_USERS_ORDER_BY = "SELECT * FROM users ORDER BY";
    private static final String SQL_SELECT_ALL_USERS_ORDER_BY_NAME = "SELECT * FROM users ORDER BY name";
    private static final String SQL_SAVE_USER = "" +
            "INSERT INTO users " +
            "(user_id, name, surname, email, login, password, type) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_USER = "" +
            "UPDATE users " +
            "SET name = ?, surname = ?, email = ?, login = ?, password = ?, type = ? " +
            "WHERE user_id = ?";
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE user_id = ?";
    private static final String SQL_SELECT_ALL_EVENTS_BY_USER_ID = "" +
            "SELECT event_id " +
            "FROM event_subscriptions " +
            "WHERE user_id = ?";
    private static final String SQL_ADD_NEW_EVENT = "" +
            "INSERT INTO event_subscriptions " +
            "(user_id, event_id) " +
            "VALUES (?, ?)";
    private static final String SQL_DELETE_EVENT = "" +
            "DELETE " +
            "FROM event_subscriptions " +
            "WHERE user_id = ? AND event_id = ?";
    private static final String SQL_SELECT_EVENT_NAME_BY_ID = "SELECT name FROM events WHERE event_id = ?";
    private static final String SORT_BY_COLUMN = "name";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    private UserRowMapper userRowMapper;

    @Override
    public User getById(long id) {

        User user;
        try {
            user = getUser(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new DataNotFoundException(
                    "There is no such user with id = " + id, ex);
        } catch (DataAccessException ex) {
            throw new DAOException(
                    "Something went wrong when trying to call the method getById()", ex);
        }
        return user;
    }

    private User getUser(long id) {
        User user = jdbcTemplate.queryForObject(
                SQL_SELECT_USER_BY_ID,
                userRowMapper,
                id
        );
        return user;
    }

    @Override
    public List<User> findAll(Pageable page) {

        String sqlQuery = getSqlQuery(page);

        List<User> users;
        try {
            users = getUsers(sqlQuery);
        } catch (DataAccessException ex) {
            throw new DAOException(
                    "Something went wrong when trying to call the method findAll()", ex);
        }
        return users;
    }

    private List<User> getUsers(String query) {
        List<User> users = jdbcTemplate.query(
                query,
                userRowMapper
        );
        return users;
    }

    private String getSqlQuery(Pageable pageable) {
        String query = SQL_SELECT_ALL_USERS_ORDER_BY_NAME;
        if (pageable != null) {
            query = buildSqlQuery(pageable);
        }
        return query;
    }

    private String buildSqlQuery(Pageable pageable) {
        String query;
        if (pageable.getSort().isEmpty()) {
            Sort.Order order = Sort.Order.by(SORT_BY_COLUMN);

            query = collectSqlQuery(pageable, order);
        } else {
            Sort.Order order = pageable.getSort().iterator().next();

            query = collectSqlQuery(pageable, order);
        }
        return query;
    }

    private String collectSqlQuery(Pageable pageable, Sort.Order sort) {

        String sortProperty = sort.getProperty();
        String sortDirectionName = sort.getDirection().name();
        String limit = "LIMIT";
        int pageSize = pageable.getPageSize();
        String offset = "OFFSET";
        long pageOffset = pageable.getOffset();

        String result = String.format(
                SQL_SELECT_ALL_USERS_ORDER_BY + " %1$s %2$s %3$s %4$d %5$s %6$d",
                sortProperty, sortDirectionName, limit, pageSize, offset, pageOffset);

        return result;
    }

    @Override
    public void save(User user) {

        long id = user.getId();
        String name = user.getName();
        String surname = user.getSurname();
        String email = user.getEmail();
        String login = user.getLogin();
        String password = user.getPassword();
        String type = user.getType();

        try {
            jdbcTemplate.update(
                    SQL_SAVE_USER,
                    id, name, surname, email, login, password, type
            );
        } catch (DataAccessException ex) {
            throw new DAOException(
                    "Something went wrong when trying to call the method save()", ex);
        }
    }

    @Override
    public void update(User user) {

        long id = user.getId();
        String name = user.getName();
        String surname = user.getSurname();
        String email = user.getEmail();
        String login = user.getLogin();
        String password = user.getPassword();
        String type = user.getType();

        try {
            jdbcTemplate.update(
                    SQL_UPDATE_USER,
                    name, surname, email, login, password, type, id
            );
        } catch (DataAccessException ex) {
            throw new DAOException(
                    "Something went wrong when trying to call the method update()", ex);
        }
    }

    @Override
    public void delete(long id) {
        try {
            jdbcTemplate.update(
                    SQL_DELETE_USER,
                    id
            );
        } catch (DataAccessException ex) {
            throw new DAOException(
                    "Something went wrong when trying to call the method delete()", ex);
        }
    }

    @Override
    public List<String> getAllEventsByUserId(long id) {

        List<Long> eventIDs = getEventIDs(id);

        List<String> events = convertToNames(eventIDs);

        return events;
    }

    private List<String> convertToNames(List<Long> input) {
        List<String> names;
        try {
            names = getEventNames(input);
        } catch (DataAccessException ex) {
            throw new DAOException(
                    "Something went wrong in the process of receiving event names " +
                            "when trying to call the method convertToNames()", ex);
        }
        return names;
    }

    private List<Long> getEventIDs(long id) {
        List<Long> dataIDs;
        try {
            dataIDs = jdbcTemplate.queryForList(
                    SQL_SELECT_ALL_EVENTS_BY_USER_ID,
                    Long.class,
                    id
            );
        } catch (DataAccessException ex) {
            throw new DAOException(
                    "Something went wrong in the process of receiving IDs " +
                            "when trying to call the method getEventIDs()", ex);
        }
        return dataIDs;
    }

    private List<String> getEventNames(List<Long> inputData) {

        List<String> result = new ArrayList<>();
        for (long id : inputData) {
            String eventName = jdbcTemplate.queryForObject(
                    SQL_SELECT_EVENT_NAME_BY_ID,
                    String.class,
                    id
            );
            result.add(eventName);
        }
        return result;
    }

    @Override
    public void addNewEvent(long userId, long eventId) {
        try {
            jdbcTemplate.update(
                    SQL_ADD_NEW_EVENT,
                    userId, eventId
            );
        } catch (DataAccessException ex) {
            throw new DAOException(
                    "Something went wrong when trying to call the method addNewEvent()", ex);
        }
    }

    @Override
    public void removeEvent(long userId, long eventId) {
        try {
            jdbcTemplate.update(
                    SQL_DELETE_EVENT,
                    userId, eventId
            );
        } catch (DataAccessException ex) {
            throw new DAOException(
                    "Something went wrong when trying to call the method removeEvent()", ex);
        }
    }
}


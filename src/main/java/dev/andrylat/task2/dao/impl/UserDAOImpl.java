package dev.andrylat.task2.dao.impl;

import dev.andrylat.task2.dao.UserDAO;
import dev.andrylat.task2.entities.User;
import dev.andrylat.task2.mappers.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO {
    private static final String SQL_SELECT_USER_BY_ID = "SELECT * FROM users WHERE user_id = ?";
    private static final String SQL_SELECT_ALL_USERS = "SELECT * FROM users";
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

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    private UserRowMapper userRowMapper;

    @Override
    public User getById(long id) {

        User theUser = jdbcTemplate.queryForObject(
                SQL_SELECT_USER_BY_ID,
                userRowMapper,
                new Object[]{id}
        );
        return theUser;
    }

    @Override
    public List<User> findAll(Pageable page) {

        String sqlQuery;
        if (page != null) {
            sqlQuery = getSqlQuery(page);
        } else {
            sqlQuery = SQL_SELECT_ALL_USERS_ORDER_BY_NAME;
        }

        List<User> users = jdbcTemplate.query(
                sqlQuery,
                userRowMapper
        );
        return users;
    }

    private String getSqlQuery(Pageable pageable) {
        String query;
        if (pageable.getSort().isEmpty()) {
            Sort.Order order = Sort.Order.by("name");

            query = collectSqlQuery(pageable, order);
        } else {
            Sort.Order order = pageable.getSort().toList().get(0);

            query = collectSqlQuery(pageable, order);
        }
        return query;
    }

    private String collectSqlQuery(Pageable pageable, Sort.Order sort) {

        String query = SQL_SELECT_ALL_USERS
                + " ORDER BY " + sort.getProperty() + " " + sort.getDirection().name()
                + " LIMIT " + pageable.getPageSize()
                + " OFFSET " + pageable.getOffset();

        return query;
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

        jdbcTemplate.update(
                SQL_SAVE_USER,
                id, name, surname, email, login, password, type
        );
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

        jdbcTemplate.update(
                SQL_UPDATE_USER,
                name, surname, email, login, password, type, id
        );
    }

    @Override
    public void delete(long id) {

        jdbcTemplate.update(
                SQL_DELETE_USER,
                id
        );
    }

    @Override
    public List<Long> getAllEventsByUserId(long id) {

        List<Long> events = jdbcTemplate.queryForList(
                SQL_SELECT_ALL_EVENTS_BY_USER_ID,
                new Object[]{id},
                Long.class
        );
        return events;
    }

    @Override
    public void addNewEvent(long firstId, long secondId) {

        jdbcTemplate.update(
                SQL_ADD_NEW_EVENT,
                firstId, secondId
        );
    }

    @Override
    public void removeEvent(long firstId, long secondId) {

        jdbcTemplate.update(
                SQL_DELETE_EVENT,
                firstId, secondId
        );
    }
}

package dev.andrylat.task2.dao.impl;

import dev.andrylat.task2.dao.EventDAO;
import dev.andrylat.task2.entities.Event;
import dev.andrylat.task2.mappers.EventRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("eventDAO")
public class EventDAOImpl implements EventDAO {
    private static final String SQL_SELECT_EVENT_BY_ID = "SELECT * FROM events WHERE event_id = ?";
    private static final String SQL_SELECT_ALL_EVENTS = "SELECT * FROM events";
    private static final String SQL_SELECT_ALL_EVENTS_ORDER_BY_NAME = "SELECT * FROM events ORDER BY name";
    private static final String SQL_SAVE_EVENT = "" +
            "INSERT INTO events " +
            "(event_id, name, date, price, status, description, location_id)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_EVENT = "" +
            "UPDATE events " +
            "SET name = ?, date = ?, price = ?, status = ?, description = ?, location_id = ? " +
            "WHERE event_id = ?";
    private static final String SQL_DELETE_EVENT = "DELETE FROM events WHERE event_id = ?";
    private static final String SQL_SELECT_ALL_CATEGORIES_BY_EVENT_ID = "" +
            "SELECT category_id " +
            "FROM events_categories " +
            "WHERE event_id = ?";
    private static final String SQL_ADD_NEW_CATEGORY = "" +
            "INSERT INTO events_categories (event_id, category_id) " +
            "VALUES (?, ?)";
    private static final String SQL_DELETE_CATEGORY = "" +
            "DELETE " +
            "FROM events_categories " +
            "WHERE event_id = ? AND category_id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EventDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    private EventRowMapper eventRowMapper;

    @Override
    public Event getById(long id) {

        Event event = jdbcTemplate.queryForObject(
                SQL_SELECT_EVENT_BY_ID,
                new Object[]{id},
                eventRowMapper
        );
        return event;
    }

    @Override
    public List<Event> findAll(Pageable page) {

        String sqlQuery;
        if (page != null) {
            sqlQuery = getSqlQuery(page);
        } else {
            sqlQuery = SQL_SELECT_ALL_EVENTS_ORDER_BY_NAME;
        }
        List<Event> events = jdbcTemplate.query(
                sqlQuery,
                eventRowMapper
        );
        return events;
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

        String query = SQL_SELECT_ALL_EVENTS
                + " ORDER BY " + sort.getProperty() + " " + sort.getDirection().name()
                + " LIMIT " + pageable.getPageSize()
                + " OFFSET " + pageable.getOffset();

        return query;
    }

    @Override
    public void save(Event event) {

        long id = event.getId();
        String title = event.getTitle();
        Date date = event.getDate();
        int price = event.getPrice();
        String status = event.getStatus();
        String description = event.getDescription();
        long locationId = event.getLocationId();

        jdbcTemplate.update(
                SQL_SAVE_EVENT,
                id, title, date, price, status, description, locationId
        );
    }

    @Override
    public void update(Event event) {

        long id = event.getId();
        String title = event.getTitle();
        Date date = event.getDate();
        int price = event.getPrice();
        String status = event.getStatus();
        String description = event.getDescription();
        long locationId = event.getLocationId();

        jdbcTemplate.update(
                SQL_UPDATE_EVENT,
                title, date, price, status, description, locationId, id
        );
    }

    @Override
    public void delete(long id) {

        jdbcTemplate.update(
                SQL_DELETE_EVENT,
                id
        );
    }

    @Override
    public List<Long> getAllCategoriesByEventId(long id) {

        List<Long> categories = jdbcTemplate.queryForList(
                SQL_SELECT_ALL_CATEGORIES_BY_EVENT_ID,
                new Object[]{id},
                Long.class
        );

        return categories;
    }

    @Override
    public void addNewCategory(long firstId, long secondId) {

        jdbcTemplate.update(
                SQL_ADD_NEW_CATEGORY,
                firstId, secondId
        );
    }

    @Override
    public void removeCategory(long firstId, long secondId) {

        jdbcTemplate.update(
                SQL_DELETE_CATEGORY,
                firstId, secondId
        );
    }
}


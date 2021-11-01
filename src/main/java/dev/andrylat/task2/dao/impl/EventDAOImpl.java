package dev.andrylat.task2.dao.impl;

import dev.andrylat.task2.dao.EventDAO;
import dev.andrylat.task2.entities.Event;
import dev.andrylat.task2.exceptions.DAOException;
import dev.andrylat.task2.exceptions.DataNotFoundException;
import dev.andrylat.task2.mappers.EventRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository("eventDAO")
public class EventDAOImpl implements EventDAO {
    private static final String SQL_SELECT_EVENT_BY_ID = "SELECT * FROM events WHERE event_id = ?";
    private static final String SQL_SELECT_ALL_EVENTS_ORDER_BY = "SELECT * FROM events ORDER BY";
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
    private static final String SQL_SELECT_CATEGORY_NAME_BY_ID = "" +
            "SELECT name " +
            "FROM categories " +
            "WHERE category_id = ?";
    private static final String SORT_BY_COLUMN = "name";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EventDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    private EventRowMapper eventRowMapper;

    @Override
    public Event getById(long id) {

        Event event;
        try {
            event = getEvent(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new DataNotFoundException(
                    "There is no such event with id = " + id, ex);
        } catch (DataAccessException ex) {
            throw new DAOException(
                    "Something went wrong when trying to call the method getById()", ex);
        }
        return event;
    }

    private Event getEvent(long id) {
        Event event = jdbcTemplate.queryForObject(
                SQL_SELECT_EVENT_BY_ID,
                eventRowMapper,
                id
        );
        return event;
    }

    @Override
    public List<Event> findAll(Pageable page) {

        String sqlQuery = getSqlQuery(page);

        List<Event> events;
        try {
            events = getEvents(sqlQuery);
        } catch (DataAccessException ex) {
            throw new DAOException(
                    "Something went wrong when trying to call the method findAll()", ex);
        }
        return events;
    }

    private List<Event> getEvents(String query) {
        List<Event> events = jdbcTemplate.query(
                query,
                eventRowMapper
        );
        return events;
    }

    private String getSqlQuery(Pageable pageable) {
        String query = SQL_SELECT_ALL_EVENTS_ORDER_BY_NAME;
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
                SQL_SELECT_ALL_EVENTS_ORDER_BY + " %1$s %2$s %3$s %4$d %5$s %6$d",
                sortProperty, sortDirectionName, limit, pageSize, offset, pageOffset);

        return result;
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

        try {
            jdbcTemplate.update(
                    SQL_SAVE_EVENT,
                    id, title, date, price, status, description, locationId
            );
        } catch (DataAccessException ex) {
            throw new DAOException(
                    "Something went wrong when trying to call the method save()", ex);
        }
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

        try {
            jdbcTemplate.update(
                    SQL_UPDATE_EVENT,
                    title, date, price, status, description, locationId, id
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
                    SQL_DELETE_EVENT,
                    id
            );
        } catch (DataAccessException ex) {
            throw new DAOException(
                    "Something went wrong when trying to call the method delete()", ex);
        }
    }

    @Override
    public List<String> getAllCategoriesByEventId(long id) {

        List<Long> categoryIDs = getCategoryIDs(id);

        List<String> categoryNames = convertToNames(categoryIDs);

        return categoryNames;
    }

    private List<String> convertToNames(List<Long> input) {
        List<String> names;
        try {
            names = getCategoryNames(input);
        } catch (DataAccessException ex) {
            throw new DAOException(
                    "Something went wrong in the process of receiving category names " +
                            "when trying to call the method convertToNames()", ex);
        }
        return names;
    }

    private List<Long> getCategoryIDs(long id) {
        List<Long> dataIDs;
        try {
            dataIDs = jdbcTemplate.queryForList(
                    SQL_SELECT_ALL_CATEGORIES_BY_EVENT_ID,
                    Long.class,
                    id
            );
        } catch (DataAccessException ex) {
            throw new DAOException(
                    "Something went wrong in the process of receiving IDs " +
                            "when trying to call the method getCategoryIDs()", ex);
        }
        return dataIDs;
    }

    private List<String> getCategoryNames(List<Long> inputData) {

        List<String> result = new ArrayList<>();
        for (long id : inputData) {
            String categoryName = jdbcTemplate.queryForObject(
                    SQL_SELECT_CATEGORY_NAME_BY_ID,
                    String.class,
                    id
            );
            result.add(categoryName);
        }
        return result;
    }

    @Override
    public void addNewCategory(long eventId, long categoryId) {
        try {
            jdbcTemplate.update(
                    SQL_ADD_NEW_CATEGORY,
                    eventId, categoryId
            );
        } catch (DataAccessException ex) {
            throw new DAOException(
                    "Something went wrong when trying to call the method addNewCategory()", ex);
        }
    }

    @Override
    public void removeCategory(long eventId, long categoryId) {
        try {
            jdbcTemplate.update(
                    SQL_DELETE_CATEGORY,
                    eventId, categoryId
            );
        } catch (DataAccessException ex) {
            throw new DAOException(
                    "Something went wrong when trying to call the method removeCategory()", ex);
        }
    }
}


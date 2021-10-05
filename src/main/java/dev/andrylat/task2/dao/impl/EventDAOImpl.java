package dev.andrylat.task2.dao.impl;

import dev.andrylat.task2.dao.EventDAO;
import dev.andrylat.task2.entities.Event;
import dev.andrylat.task2.mappers.EventRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("eventDAO")
public class EventDAOImpl implements EventDAO {
    private static final String SQL_SELECT_EVENT = "SELECT * FROM events WHERE event_id = ?";
    private static final String SQL_SELECT_ALL_EVENTS = "SELECT * FROM events";
    private static final String SQL_SAVE_EVENT = "INSERT INTO events " +
            "(event_id, name, date, price, status, description, location_id)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_EVENT = "UPDATE events " +
            "SET name = ?, date = ?, price = ?, status = ?, description = ?, location_id = ? " +
            "WHERE event_id = ?";
    private static final String SQL_DELETE_EVENT = "DELETE FROM events WHERE event_id = ?";

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
                SQL_SELECT_EVENT,
                eventRowMapper,
                new Object[]{id}
        );
        return event;
    }

    @Override
    public List<Event> findAll() {

        List<Event> events = jdbcTemplate.query(
                SQL_SELECT_ALL_EVENTS,
                eventRowMapper
        );
        return events;
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
}

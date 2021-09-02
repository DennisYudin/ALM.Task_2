package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.Event;
import org.springframework.data.domain.Sort;
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

    public EventDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Event getEvent(long id) {

        Event event = jdbcTemplate.queryForObject(
                SQL_SELECT_EVENT,
                new Object[]{id},
                new EventRowMapper()
        );
        return event;
    }

    @Override
    public List<Event> getEvents() {

        List<Event> events = jdbcTemplate.query(
                SQL_SELECT_ALL_EVENTS,
                new EventRowMapper()
        );
        return events;
    }

    @Override
    public void saveEvent(Event event) {

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
    public void updateEvent(Event event) {

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
    public void deleteEvent(long id) {

        jdbcTemplate.update(
                SQL_DELETE_EVENT,
                id
        );
    }

    @Override
    public List<Event> sortByName() {

        return null;
    }

    private Sort sortByNameAsc() {
//        Sort name = new Sort(Sort.Direction.ASC, "name");
//        return name;
        return null;
    }

    @Override
    public List<Event> sortByPrice() {
        return null;
    }

    @Override
    public List<Event> sortByDate() {
        return null;
    }
}

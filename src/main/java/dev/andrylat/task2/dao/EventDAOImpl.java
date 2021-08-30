package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.Event;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class EventDAOImpl implements EventDAO {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public EventDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Event getEventById(long theId) {
        return null;
    }

    @Override
    public List<Event> getEvents() {

        String sqlQuery = "SELECT * FROM events";

        List<Event> events = jdbcTemplate.query(
                sqlQuery,
                new EventRowMapper());

        return events;
    }

    @Override
    public void saveEvent(Event theEvent) {

    }

    @Override
    public void updateEvent(Event theEvent) {

    }

    @Override
    public void deleteEvent(long theId) {

    }

    @Override
    public List<Event> sortByName() {
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

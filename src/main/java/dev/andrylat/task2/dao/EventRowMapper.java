package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.Event;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EventRowMapper implements RowMapper<Event> {

    @Override
    public Event mapRow(ResultSet rs, int rowNum) throws SQLException {

        Event event = new Event();

        long id = rs.getLong("event_id");
        String name = rs.getString("name");
        Date date = rs.getDate("date");
        int price = rs.getInt("price");
        String status = rs.getString("status");
        String description = rs.getString("description");
        long locationId = rs.getLong("location_id");

        event.setId(id);
        event.setTitle(name);
        event.setDate(date);
        event.setPrice(price);
        event.setStatus(status);
        event.setDescription(description);
        event.setLocationId(locationId);

        return event;
    }
}

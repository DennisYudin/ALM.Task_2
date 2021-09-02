package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.Ticket;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class TicketRowMapper implements RowMapper<Ticket> {

    @Override
    public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {

        Ticket ticket = new Ticket();

        long id = rs.getLong("ticket_id");
        String eventName = rs.getString("event_name");
        String uniqueNumber = rs.getString("unique_number");
        Date creationDate = rs.getDate("creation_date");
        String status = rs.getString("status");
        long userId = rs.getLong("user_id");
        long eventId = rs.getLong("event_id");

        ticket.setId(id);
        ticket.setEventName(eventName);
        ticket.setUniqueCode(uniqueNumber);
        ticket.setCreationDate(creationDate);
        ticket.setStatus(status);
        ticket.setUserId(userId);
        ticket.setEventId(eventId);

        return ticket;
    }
}

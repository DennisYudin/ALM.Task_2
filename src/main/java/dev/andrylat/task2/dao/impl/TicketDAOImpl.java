package dev.andrylat.task2.dao.impl;

import dev.andrylat.task2.dao.TicketDAO;
import dev.andrylat.task2.entities.Ticket;
import dev.andrylat.task2.mappers.TicketRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("ticketDAO")
public class TicketDAOImpl implements TicketDAO {
    private static final String SQL_SELECT_TICKET = "SELECT * FROM tickets WHERE ticket_id = ?";
    private static final String SQL_SELECT_ALL_TICKETS = "SELECT * FROM tickets";
    private static final String SQL_SAVE_TICKET = "INSERT INTO tickets " +
            "(ticket_id, event_name, unique_number, creation_date, status, user_id, event_id)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_TICKET = "UPDATE tickets " +
            "SET event_name = ?, unique_number = ?, creation_date = ?, status = ?, user_id = ?, event_id = ? " +
            "WHERE ticket_id = ?";
    private static final String SQL_DELETE_TICKET = "DELETE FROM tickets WHERE ticket_id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TicketDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    private TicketRowMapper ticketRowMapper;

    @Override
    public Ticket getById(long id) {

        Ticket ticket = jdbcTemplate.queryForObject(
                SQL_SELECT_TICKET,
                ticketRowMapper,
                new Object[]{id}
        );
        return ticket;
    }

    @Override
    public List<Ticket> findAll() {

        List<Ticket> tickets = jdbcTemplate.query(
                SQL_SELECT_ALL_TICKETS,
                ticketRowMapper
        );
        return tickets;
    }

    @Override
    public void save(Ticket ticket) {

        long id = ticket.getId();
        String eventName = ticket.getEventName();
        String uniqueNumber = ticket.getUniqueCode();
        Date date = ticket.getCreationDate();
        String status = ticket.getStatus();
        long userId = ticket.getUserId();
        long eventId = ticket.getEventId();

        jdbcTemplate.update(
                SQL_SAVE_TICKET,
                id, eventName, uniqueNumber, date, status, userId, eventId
        );
    }

    @Override
    public void update(Ticket ticket) {

        long id = ticket.getId();
        String eventName = ticket.getEventName();
        String uniqueNumber = ticket.getUniqueCode();
        Date date = ticket.getCreationDate();
        String status = ticket.getStatus();
        long userId = ticket.getUserId();
        long eventId = ticket.getEventId();

        jdbcTemplate.update(
                SQL_UPDATE_TICKET,
                eventName, uniqueNumber, date, status, userId, eventId, id
        );
    }

    @Override
    public void delete(long id) {

        jdbcTemplate.update(
                SQL_DELETE_TICKET,
                id
        );
    }
}

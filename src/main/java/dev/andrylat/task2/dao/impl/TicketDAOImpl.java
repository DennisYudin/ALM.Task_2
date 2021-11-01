package dev.andrylat.task2.dao.impl;

import dev.andrylat.task2.dao.TicketDAO;
import dev.andrylat.task2.entities.Ticket;
import dev.andrylat.task2.exceptions.DAOException;
import dev.andrylat.task2.exceptions.DataNotFoundException;
import dev.andrylat.task2.mappers.TicketRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("ticketDAO")
public class TicketDAOImpl implements TicketDAO {
    private static final String SQL_SELECT_TICKET_BY_ID = "SELECT * FROM tickets WHERE ticket_id = ?";
    private static final String SQL_SELECT_ALL_TICKETS_ORDER_BY = "SELECT * FROM tickets ORDER BY";
    private static final String SQL_SELECT_ALL_TICKETS_ORDER_BY_EVENT_NAME = "" +
            "SELECT * FROM tickets " +
            "ORDER BY event_name";
    private static final String SQL_SAVE_TICKET = "" +
            "INSERT INTO tickets " +
            "(ticket_id, event_name, unique_number, creation_date, status, user_id, event_id)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_TICKET = "" +
            "UPDATE tickets " +
            "SET event_name = ?, unique_number = ?, creation_date = ?, status = ?, user_id = ?, event_id = ? " +
            "WHERE ticket_id = ?";
    private static final String SQL_DELETE_TICKET = "DELETE FROM tickets WHERE ticket_id = ?";
    private static final String SORT_BY_COLUMN = "event_name";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TicketDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    private TicketRowMapper ticketRowMapper;

    @Override
    public Ticket getById(long id) {

        Ticket ticket;
        try {
            ticket = getTicket(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new DataNotFoundException(
                    "There is no such ticket with id = " + id, ex);
        } catch (DataAccessException ex) {
            throw new DAOException(
                    "Something went wrong when trying to call the method getById()", ex);
        }
        return ticket;
    }

    private Ticket getTicket(long id) {
        Ticket ticket = jdbcTemplate.queryForObject(
                SQL_SELECT_TICKET_BY_ID,
                ticketRowMapper,
                id
        );
        return ticket;
    }

    @Override
    public List<Ticket> findAll(Pageable page) {

        String sqlQuery = getSqlQuery(page);

        List<Ticket> tickets;
        try {
            tickets = getTickets(sqlQuery);
        } catch (DataAccessException ex) {
            throw new DAOException(
                    "Something went wrong when trying to call the method findAll()", ex);
        }
        return tickets;
    }

    private List<Ticket> getTickets(String query) {
        List<Ticket> tickets = jdbcTemplate.query(
                query,
                ticketRowMapper
        );
        return tickets;
    }

    private String getSqlQuery(Pageable pageable) {
        String query = SQL_SELECT_ALL_TICKETS_ORDER_BY_EVENT_NAME;
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
                SQL_SELECT_ALL_TICKETS_ORDER_BY + " %1$s %2$s %3$s %4$d %5$s %6$d",
                sortProperty, sortDirectionName, limit, pageSize, offset, pageOffset);

        return result;
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

        try {
            jdbcTemplate.update(
                    SQL_SAVE_TICKET,
                    id, eventName, uniqueNumber, date, status, userId, eventId
            );
        } catch (DataAccessException ex) {
            throw new DAOException(
                    "Something went wrong when trying to call the method save()", ex);
        }
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

        try {
            jdbcTemplate.update(
                    SQL_UPDATE_TICKET,
                    eventName, uniqueNumber, date, status, userId, eventId, id
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
                    SQL_DELETE_TICKET,
                    id
            );
        } catch (DataAccessException ex) {
            throw new DAOException(
                    "Something went wrong when trying to call the method delete()", ex);
        }
    }
}


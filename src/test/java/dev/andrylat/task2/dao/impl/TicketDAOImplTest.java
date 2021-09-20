package dev.andrylat.task2.dao.impl;

import dev.andrylat.task2.configs.AppConfigTest;
import dev.andrylat.task2.dao.TicketDAO;
import dev.andrylat.task2.entities.Ticket;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfigTest.class)
@Sql(scripts = {"file:src/test/resources/createTables.sql",
        "file:src/test/resources/populateTables.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "file:src/test/resources/cleanUpTables.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TicketDAOImplTest {
    private static final String SQL_SELECT_TICKET_ID = "SELECT ticket_id " +
            "FROM tickets " +
            "WHERE event_name = ? AND unique_number = ? AND creation_date = ? " +
            "AND status = ? AND user_id = ? AND event_id = ?";
    private static final String SQL_SELECT_ALL_TICKETS_ID = "SELECT ticket_id FROM tickets";

    @Autowired
    private TicketDAO ticketDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void getById_ShouldGetTicketById_WhenInputIsId() throws ParseException {

        long id = 3000;

        String expectedName = "Oxxxymiron concert";
        String expectedUniqueNumber = "123456789";
        Date expectedDate = getDate("17-02-1992 20:45:00");
        String expectedStatus = "actual";
        long expectedUserId = 2000;
        long expectedEventId = 1000;

        String actualName = ticketDAO.getById(id).getEventName();
        String actualUniqueNumber = ticketDAO.getById(id).getUniqueCode();
        Date actualDate = ticketDAO.getById(id).getCreationDate();
        String actualStatus = ticketDAO.getById(id).getStatus();
        long actualUserId = ticketDAO.getById(id).getUserId();
        long actualEventId = ticketDAO.getById(id).getEventId();

        assertEquals(expectedName, actualName);
        assertEquals(expectedUniqueNumber, actualUniqueNumber);
        assertEquals(expectedDate, actualDate);
        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedUserId, actualUserId);
        assertEquals(expectedEventId, actualEventId);
    }

    @Test
    public void findAll_ShouldGetAllTickets_WhenCallMethod() throws ParseException {

        List<Ticket> actualTickets = ticketDAO.findAll();
        List<Long> expectedId = jdbcTemplate.queryForList(
                SQL_SELECT_ALL_TICKETS_ID,
                Long.class
        );
        int expectedSize = expectedId.size();
        int actualSize = actualTickets.size();

        assertEquals(expectedSize, actualSize);

        for (Ticket ticket : actualTickets) {
            long actualTicketId = ticket.getId();

            assertTrue(expectedId.contains(actualTicketId));
        }
    }

    @Test
    public void save_ShouldSaveTicket_WhenInputIsTicketObjectWithDetails() throws ParseException {

        Date concertDate = getDate("17-02-1992 16:30:00");

        Ticket newTicket = getTicket(
                3002, "circus du soleil",
                "0000000000", concertDate,
                "actual", 2000, 1000
        );

        ticketDAO.save(newTicket);

        String checkName = "circus du soleil";
        String checkUniquenamber = "0000000000";
        Date checkDate = concertDate;
        String checkStatus = "actual";
        long checkUserId = 2000;
        long checkEventId = 1000;

        long expectedId = 3002;
        Long actualId = jdbcTemplate.queryForObject(
                SQL_SELECT_TICKET_ID,
                new Object[]{checkName, checkUniquenamber, checkDate,
                        checkStatus, checkUserId, checkEventId},
                Long.class
        );
        assertEquals(expectedId, actualId);
    }

    @Test
    public void update_ShouldUpdateExistedTicket_WhenInputIsTicketObjectWithDetails() throws ParseException {

        Date concertDate = getDate("01-09-2021 22:13:00");

        Ticket updatedTicket = getTicket(
                3000, "Comedy club",
                "111111111", concertDate,
                "actual", 2000, 1000
        );

        ticketDAO.update(updatedTicket);

        String checkName = "Comedy club";
        String checkUniqueNumber = "111111111";
        Date checkDate = concertDate;
        String checkStatus = "actual";
        long checkUserId = 2000;
        long checkEventId = 1000;

        long expectedId = 3000;
        Long actualId = jdbcTemplate.queryForObject(
                SQL_SELECT_TICKET_ID,
                new Object[]{checkName, checkUniqueNumber, checkDate,
                        checkStatus, checkUserId, checkEventId},
                Long.class
        );
        assertEquals(expectedId, actualId);
    }

    @Test
    public void delete_ShouldDeleteTicketById_WhenInputIsId() throws ParseException {

        long ticketId = 3000;

        ticketDAO.delete(ticketId);

        List<Long> actualId = jdbcTemplate.queryForList(
                SQL_SELECT_ALL_TICKETS_ID,
                Long.class
        );
        int expectedSize = 1;
        int actualSize = actualId.size();

        int checkedId = 3000;

        assertEquals(expectedSize, actualSize);
        assertFalse(actualId.contains(checkedId));
    }

    private Ticket getTicket(long id, String name,
                             String uniqueNumber, Date date,
                             String status, long firstForeignKey,
                             long secondForeignKey) {
        Ticket ticket = new Ticket();
        ticket.setId(id);
        ticket.setEventName(name);
        ticket.setUniqueCode(uniqueNumber);
        ticket.setCreationDate(date);
        ticket.setStatus(status);
        ticket.setUserId(firstForeignKey);
        ticket.setEventId(secondForeignKey);
        return ticket;
    }

    private Date getDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(
                "dd-M-yyyy hh:mm:ss");
        String dateInString = date;
        Date convertedDate = sdf.parse(dateInString);
        return convertedDate;
    }
}


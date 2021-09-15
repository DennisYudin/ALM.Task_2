package dev.andrylat.task2.implementations;

import configs.AppConfigTest;
import dev.andrylat.task2.dao.EventDAO;
import dev.andrylat.task2.entities.Event;
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
        "file:src/test/resources/populateTablesWithoutTicketTable.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "file:src/test/resources/cleanUpTables.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class EventDAOImplTest {
    private static final String SQL_SELECT_EVENT_ID = "SELECT event_id " +
            "FROM events " +
            "WHERE name = ? AND date = ? AND price = ? AND status = ? AND description = ? AND location_id = ?";
    private static final String SQL_SELECT_ALL_EVENTS_ID = "SELECT event_id FROM events";

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void getEvent_ShouldGetEventById_WhenInputIsId() throws ParseException {

        long id = 1000;

        String expectedName = "Oxxxymiron concert";
        Date expectedDate = getDate("13-08-2021 18:23:00");
        int expectedPrice = 5000;
        String expectedStatus = "actual";
        String expectedDescription = "Oxxxymiron is";
        long expectedLocationId = 101;

        String actualName = eventDAO.getEvent(id).getTitle();
        Date actualDate = eventDAO.getEvent(id).getDate();
        int actualPrice = eventDAO.getEvent(id).getPrice();
        String actualStatus = eventDAO.getEvent(id).getStatus();
        String actualDescription = eventDAO.getEvent(id).getDescription();
        long actualLocationId = eventDAO.getEvent(id).getLocationId();

        assertEquals(expectedName, actualName);
        assertEquals(expectedDate, actualDate);
        assertEquals(expectedPrice, actualPrice);
        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedDescription, actualDescription);
        assertEquals(expectedLocationId, actualLocationId);
    }

    @Test
    public void findAll_ShouldGetAllEvents_WhenCallMethod() throws ParseException {

        List<Event> actualEvents = eventDAO.findAll();
        List<Long> expectedId = jdbcTemplate.queryForList(
                SQL_SELECT_ALL_EVENTS_ID,
                Long.class
        );
        int expectedSize = expectedId.size();
        int actualSize = actualEvents.size();

        assertEquals(expectedSize, actualSize);

        for (Event event : actualEvents) {
            long actualEventId = event.getId();

            assertTrue(expectedId.contains(actualEventId));
        }
    }

    @Test
    public void save_ShouldSaveEvent_WhenInputIsEventObjectWithDetails() throws ParseException {

        Date concertDate = getDate("17-02-1992 16:30:00");

        Event event = getEvent(
                1002, "Leonid Agutin",
                concertDate, 10000,
                "cancelled", "Wondeful concert...",
                100
        );

        eventDAO.save(event);

        String checkName = "Leonid Agutin";
        Date checkDate = concertDate;
        int checkPrice = 10000;
        String checkStatus = "cancelled";
        String checkDesc = "Wondeful concert...";
        long checkForeignKey = 100;

        long expectedId = 1002;
        Long actualId = jdbcTemplate.queryForObject(
                SQL_SELECT_EVENT_ID,
                new Object[]{checkName, checkDate, checkPrice,
                        checkStatus, checkDesc, checkForeignKey},
                Long.class
        );
        assertEquals(expectedId, actualId);
    }

    @Test
    public void update_ShouldUpdateExistedEvent_WhenInputIsEventObjectWithDetails() throws ParseException {

        Date concertDate = getDate("17-02-1992 16:30:00");

        Event updatedEvent = getEvent(
                1000, "Leonid Agutin",
                concertDate, 1_000_000,
                "cancelled", "Wondeful concert...",
                100
        );

        eventDAO.update(updatedEvent);

        String checkName = "Leonid Agutin";
        Date checkDate = concertDate;
        int checkPrice = 1_000_000;
        String checkStatus = "cancelled";
        String checkDesc = "Wondeful concert...";
        long checkForeignKey = 100;

        long expectedId = 1000;
        Long actualId = jdbcTemplate.queryForObject(
                SQL_SELECT_EVENT_ID,
                new Object[]{checkName, checkDate, checkPrice,
                        checkStatus, checkDesc, checkForeignKey},
                Long.class
        );
        assertEquals(expectedId, actualId);
    }

    @Test
    public void delete_ShouldDeleteEventById_WhenInputIsId() throws ParseException {

        long eventId = 1000;

        eventDAO.delete(eventId);

        List<Long> actualId = jdbcTemplate.queryForList(
                SQL_SELECT_ALL_EVENTS_ID,
                Long.class
        );
        int expectedSize = 1;
        int actualSize = actualId.size();

        int checkedId = 1000;

        assertEquals(expectedSize, actualSize);
        assertFalse(actualId.contains(checkedId));
    }

    private Event getEvent(long id, String name,
                           Date date, int price,
                           String status, String desc,
                           long foreignKey) {
        Event event = new Event();
        event.setId(id);
        event.setTitle(name);
        event.setDate(date);
        event.setPrice(price);
        event.setStatus(status);
        event.setDescription(desc);
        event.setLocationId(foreignKey);
        return event;
    }

    private Date getDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(
                "dd-M-yyyy hh:mm:ss");
        String dateInString = date;
        Date convertedDate = sdf.parse(dateInString);
        return convertedDate;
    }
}

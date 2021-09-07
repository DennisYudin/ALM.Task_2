package dev.andrylat.task2.implementations;

import dev.andrylat.task2.configs.AppConfig;
import dev.andrylat.task2.dao.EventDAO;
import dev.andrylat.task2.dao.LocationDAO;
import dev.andrylat.task2.dao.TicketDAO;
import dev.andrylat.task2.dao.UserDAO;
import dev.andrylat.task2.entities.Event;
import dev.andrylat.task2.entities.Location;
import dev.andrylat.task2.entities.Ticket;
import dev.andrylat.task2.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
public class TicketDAOImplTest {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private TicketDAO ticketDAO;

    @Autowired
    private LocationDAO locationDAO;

    @Test
    public void shouldReturnTicketById() throws ParseException {

        Location location = new Location();
        location.setId(1001);
        location.setTitle("Drunk oyster");
        location.setWorkingHours("08:00-22:00");
        location.setType("bar");
        location.setAddress("FooBar street");
        location.setDescription("Bla bla bla for the test");
        location.setCapacityPeople(300);

        locationDAO.deleteLocation(location.getId());

        assertNotNull(locationDAO);
        locationDAO.saveLocation(location);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = "13-08-2021 12:00:00";
        Date concertDate = sdf.parse(dateInString);

        Event event = new Event();
        event.setId(1002);
        event.setTitle("Concert Oxxxymiron");
        event.setDate(concertDate);
        event.setPrice(1500);
        event.setStatus("relevant");
        event.setDescription("Bla bla bla just for test...");
        event.setLocationId(1001);

        ticketDAO.deleteTicket(event.getId());
        eventDAO.deleteEvent(event.getId());

        assertNotNull(eventDAO);
        eventDAO.saveEvent(event);

        User user = new User();
        user.setId(1003);
        user.setName("Dennis");
        user.setSurname("Yudin");
        user.setEmail("dennisYudin@mail.ru");
        user.setLogin("BigBoss");
        user.setPassword("1234");
        user.setType("customer");

        userDAO.deleteUser(user.getId());

        assertNotNull(userDAO);
        userDAO.saveUser(user);

        Ticket ticket = new Ticket();
        ticket.setId(1004);
        ticket.setEventName("Concert Oxxxymiron");
        ticket.setUniqueCode("123456789");
        ticket.setCreationDate(concertDate);
        ticket.setStatus("active");
        ticket.setUserId(user.getId());
        ticket.setEventId(event.getId());

        assertNotNull(ticketDAO);
        ticketDAO.saveTicket(ticket);

        long id = ticket.getId();
        Ticket ticketEventName = ticketDAO.getTicket(id);

        assertEquals("Concert Oxxxymiron", ticketEventName.getEventName());

        ticketDAO.deleteTicket(ticket.getId());
        locationDAO.deleteLocation(location.getId());
        eventDAO.deleteEvent(event.getId());
        userDAO.deleteUser(user.getId());
    }

    @Test
    public void shouldReturnAllTickets() throws ParseException {
        Location location = new Location();
        location.setId(1001);
        location.setTitle("Drunk oyster");
        location.setWorkingHours("08:00-22:00");
        location.setType("bar");
        location.setAddress("FooBar street");
        location.setDescription("Bla bla bla for the test");
        location.setCapacityPeople(300);

        locationDAO.deleteLocation(location.getId());

        assertNotNull(locationDAO);
        locationDAO.saveLocation(location);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = "13-08-2021 12:00:00";
        Date concertDate = sdf.parse(dateInString);

        Event event = new Event();
        event.setId(1002);
        event.setTitle("Concert Oxxxymiron");
        event.setDate(concertDate);
        event.setPrice(1500);
        event.setStatus("relevant");
        event.setDescription("Bla bla bla just for test...");
        event.setLocationId(1001);

        ticketDAO.deleteTicket(event.getId());
        eventDAO.deleteEvent(event.getId());

        assertNotNull(eventDAO);
        eventDAO.saveEvent(event);

        User user = new User();
        user.setId(1003);
        user.setName("Dennis");
        user.setSurname("Yudin");
        user.setEmail("dennisYudin@mail.ru");
        user.setLogin("BigBoss");
        user.setPassword("1234");
        user.setType("customer");

        userDAO.deleteUser(user.getId());

        assertNotNull(userDAO);
        userDAO.saveUser(user);

        Ticket ticket = new Ticket();
        ticket.setId(1004);
        ticket.setEventName("Concert Oxxxymiron");
        ticket.setUniqueCode("123456789");
        ticket.setCreationDate(concertDate);
        ticket.setStatus("active");
        ticket.setUserId(user.getId());
        ticket.setEventId(event.getId());

        assertNotNull(ticketDAO);
        ticketDAO.saveTicket(ticket);

        List<Ticket> tickets = ticketDAO.getTickets();

        assertTrue(tickets.size() == 1);

        ticketDAO.deleteTicket(ticket.getId());
        locationDAO.deleteLocation(location.getId());
        eventDAO.deleteEvent(event.getId());
        userDAO.deleteUser(user.getId());
    }

    @Test
    public void shouldSaveTicketIntoTable() throws ParseException {

        Location location = new Location();
        location.setId(1001);
        location.setTitle("Drunk oyster");
        location.setWorkingHours("08:00-22:00");
        location.setType("bar");
        location.setAddress("FooBar street");
        location.setDescription("Bla bla bla for the test");
        location.setCapacityPeople(300);

        locationDAO.deleteLocation(location.getId());

        assertNotNull(locationDAO);
        locationDAO.saveLocation(location);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = "13-08-2021 12:00:00";
        Date concertDate = sdf.parse(dateInString);

        Event event = new Event();
        event.setId(1002);
        event.setTitle("Concert Oxxxymiron");
        event.setDate(concertDate);
        event.setPrice(1500);
        event.setStatus("relevant");
        event.setDescription("Bla bla bla just for test...");
        event.setLocationId(1001);

        ticketDAO.deleteTicket(event.getId());
        eventDAO.deleteEvent(event.getId());

        assertNotNull(eventDAO);
        eventDAO.saveEvent(event);

        User user = new User();
        user.setId(1003);
        user.setName("Dennis");
        user.setSurname("Yudin");
        user.setEmail("dennisYudin@mail.ru");
        user.setLogin("BigBoss");
        user.setPassword("1234");
        user.setType("customer");

        userDAO.deleteUser(user.getId());

        assertNotNull(userDAO);
        userDAO.saveUser(user);

        Ticket ticket = new Ticket();
        ticket.setId(1004);
        ticket.setEventName("Concert Oxxxymiron");
        ticket.setUniqueCode("123456789");
        ticket.setCreationDate(concertDate);
        ticket.setStatus("active");
        ticket.setUserId(user.getId());
        ticket.setEventId(event.getId());

        assertNotNull(ticketDAO);
        ticketDAO.saveTicket(ticket);

        long id = ticket.getId();
        Ticket ticketEventName = ticketDAO.getTicket(id);

        assertEquals("Concert Oxxxymiron", ticketEventName.getEventName());

        ticketDAO.deleteTicket(ticket.getId());
        locationDAO.deleteLocation(location.getId());
        eventDAO.deleteEvent(event.getId());
        userDAO.deleteUser(user.getId());
    }

    @Test
    public void shouldUpdateTicket() throws ParseException {

        Location location = new Location();
        location.setId(1001);
        location.setTitle("Drunk oyster");
        location.setWorkingHours("08:00-22:00");
        location.setType("bar");
        location.setAddress("FooBar street");
        location.setDescription("Bla bla bla for the test");
        location.setCapacityPeople(300);

        locationDAO.deleteLocation(location.getId());

        assertNotNull(locationDAO);
        locationDAO.saveLocation(location);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = "13-08-2021 12:00:00";
        Date concertDate = sdf.parse(dateInString);

        Event event = new Event();
        event.setId(1002);
        event.setTitle("Concert Oxxxymiron");
        event.setDate(concertDate);
        event.setPrice(1500);
        event.setStatus("relevant");
        event.setDescription("Bla bla bla just for test...");
        event.setLocationId(1001);

        ticketDAO.deleteTicket(event.getId());
        eventDAO.deleteEvent(event.getId());

        assertNotNull(eventDAO);
        eventDAO.saveEvent(event);

        User user = new User();
        user.setId(1003);
        user.setName("Dennis");
        user.setSurname("Yudin");
        user.setEmail("dennisYudin@mail.ru");
        user.setLogin("BigBoss");
        user.setPassword("1234");
        user.setType("customer");

        userDAO.deleteUser(user.getId());

        assertNotNull(userDAO);
        userDAO.saveUser(user);

        Ticket ticket = new Ticket();
        ticket.setId(1004);
        ticket.setEventName("Concert Oxxxymiron");
        ticket.setUniqueCode("123456789");
        ticket.setCreationDate(concertDate);
        ticket.setStatus("active");
        ticket.setUserId(user.getId());
        ticket.setEventId(event.getId());

        assertNotNull(ticketDAO);
        ticketDAO.saveTicket(ticket);

        ticket.setEventName("Eminem");

        ticketDAO.updateTicket(ticket);

        long id = ticket.getId();
        Ticket ticketEventName = ticketDAO.getTicket(id);

        assertEquals("Eminem", ticketEventName.getEventName());

        ticketDAO.deleteTicket(ticket.getId());
        locationDAO.deleteLocation(location.getId());
        eventDAO.deleteEvent(event.getId());
        userDAO.deleteUser(user.getId());
    }

    @Test
    public void shouldDeleteTicket() throws ParseException {

        Location location = new Location();
        location.setId(1001);
        location.setTitle("Drunk oyster");
        location.setWorkingHours("08:00-22:00");
        location.setType("bar");
        location.setAddress("FooBar street");
        location.setDescription("Bla bla bla for the test");
        location.setCapacityPeople(300);

        locationDAO.deleteLocation(location.getId());

        assertNotNull(locationDAO);
        locationDAO.saveLocation(location);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = "13-08-2021 12:00:00";
        Date concertDate = sdf.parse(dateInString);

        Event event = new Event();
        event.setId(1002);
        event.setTitle("Concert Oxxxymiron");
        event.setDate(concertDate);
        event.setPrice(1500);
        event.setStatus("relevant");
        event.setDescription("Bla bla bla just for test...");
        event.setLocationId(1001);

        ticketDAO.deleteTicket(event.getId());
        eventDAO.deleteEvent(event.getId());

        assertNotNull(eventDAO);
        eventDAO.saveEvent(event);

        User user = new User();
        user.setId(1003);
        user.setName("Dennis");
        user.setSurname("Yudin");
        user.setEmail("dennisYudin@mail.ru");
        user.setLogin("BigBoss");
        user.setPassword("1234");
        user.setType("customer");

        userDAO.deleteUser(user.getId());

        assertNotNull(userDAO);
        userDAO.saveUser(user);

        Ticket ticket = new Ticket();
        ticket.setId(1004);
        ticket.setEventName("Concert Oxxxymiron");
        ticket.setUniqueCode("123456789");
        ticket.setCreationDate(concertDate);
        ticket.setStatus("active");
        ticket.setUserId(user.getId());
        ticket.setEventId(event.getId());

        assertNotNull(ticketDAO);
        ticketDAO.saveTicket(ticket);

        ticketDAO.deleteTicket(ticket.getId());

        locationDAO.deleteLocation(location.getId());
        eventDAO.deleteEvent(event.getId());
        userDAO.deleteUser(user.getId());
    }
}

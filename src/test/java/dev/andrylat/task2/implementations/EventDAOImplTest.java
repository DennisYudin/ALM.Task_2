package dev.andrylat.task2.implementations;

import dev.andrylat.task2.configs.AppConfig;
import dev.andrylat.task2.dao.EventDAO;
import dev.andrylat.task2.dao.LocationDAO;
import dev.andrylat.task2.entities.Event;
import dev.andrylat.task2.entities.Location;
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

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
public class EventDAOImplTest {

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private LocationDAO locationDAO;

    @Test
    public void shouldReturnEventById() throws ParseException {

        Location location = new Location();
        location.setId(1013);
        location.setTitle("Drunk oyster");
        location.setWorkingHours("08:00-22:00");
        location.setType("bar");
        location.setAddress("FooBar street");
        location.setDescription("Bla bla bla for the test");
        location.setCapacityPeople(300);

        locationDAO.deleteLocation(location.getId());

        locationDAO.saveLocation(location);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = "13-08-2021 12:00:00";
        Date concertDate = sdf.parse(dateInString);

        Event event = new Event();
        event.setId(1000);
        event.setTitle("Concert Oxxxymiron");
        event.setDate(concertDate);
        event.setPrice(1500);
        event.setStatus("relevant");
        event.setDescription("Bla bla bla just for test...");
        event.setLocationId(1013);

        eventDAO.deleteEvent(event.getId());

        assertNotNull(eventDAO);
        eventDAO.saveEvent(event);

        long id = event.getId();
        Event eventName = eventDAO.getEvent(id);

        assertEquals("Concert Oxxxymiron", eventName.getTitle());

        eventDAO.deleteEvent(event.getId());
        locationDAO.deleteLocation(location.getId());
    }

    @Test
    public void shouldReturnAllEvents() throws ParseException {

        Location location = new Location();
        location.setId(1013);
        location.setTitle("Drunk oyster");
        location.setWorkingHours("08:00-22:00");
        location.setType("bar");
        location.setAddress("FooBar street");
        location.setDescription("Bla bla bla for the test");
        location.setCapacityPeople(300);

        locationDAO.deleteLocation(location.getId());

        locationDAO.saveLocation(location);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = "13-08-2021 12:00:00";
        Date concertDate = sdf.parse(dateInString);

        Event event = new Event();
        event.setId(1000);
        event.setTitle("Concert Oxxxymiron");
        event.setDate(concertDate);
        event.setPrice(1500);
        event.setStatus("relevant");
        event.setDescription("Bla bla bla just for test...");
        event.setLocationId(1013);

        eventDAO.deleteEvent(event.getId());

        assertNotNull(eventDAO);
        eventDAO.saveEvent(event);

        List<Event> events = eventDAO.getEvents();

        assertTrue(events.size() == 1);

        eventDAO.deleteEvent(event.getId());
        locationDAO.deleteLocation(location.getId());
    }

    @Test
    public void shouldSaveEventIntoTable() throws ParseException {

        Location location = new Location();
        location.setId(1013);
        location.setTitle("Drunk oyster");
        location.setWorkingHours("08:00-22:00");
        location.setType("bar");
        location.setAddress("FooBar street");
        location.setDescription("Bla bla bla for the test");
        location.setCapacityPeople(300);

        locationDAO.deleteLocation(location.getId());

        locationDAO.saveLocation(location);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = "13-08-2021 12:00:00";
        Date concertDate = sdf.parse(dateInString);

        Event event = new Event();
        event.setId(1000);
        event.setTitle("Concert Oxxxymiron");
        event.setDate(concertDate);
        event.setPrice(1500);
        event.setStatus("relevant");
        event.setDescription("Bla bla bla just for test...");
        event.setLocationId(1013);

        eventDAO.deleteEvent(event.getId());

        assertNotNull(eventDAO);
        eventDAO.saveEvent(event);

        long id = event.getId();
        Event eventName = eventDAO.getEvent(id);

        assertEquals("Concert Oxxxymiron", eventName.getTitle());

        eventDAO.deleteEvent(event.getId());
        locationDAO.deleteLocation(location.getId());
    }

    @Test
    public void shouldUpdateEvent() throws ParseException {

        Location location = new Location();
        location.setId(1013);
        location.setTitle("Drunk oyster");
        location.setWorkingHours("08:00-22:00");
        location.setType("bar");
        location.setAddress("FooBar street");
        location.setDescription("Bla bla bla for the test");
        location.setCapacityPeople(300);

        locationDAO.deleteLocation(1013);

        locationDAO.saveLocation(location);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = "13-08-2021 12:00:00";
        Date concertDate = sdf.parse(dateInString);

        Event event = new Event();
        event.setId(1000);
        event.setTitle("Concert Oxxxymiron");
        event.setDate(concertDate);
        event.setPrice(1500);
        event.setStatus("relevant");
        event.setDescription("Bla bla bla just for test...");
        event.setLocationId(1013);

        eventDAO.deleteEvent(event.getId());

        assertNotNull(eventDAO);
        eventDAO.saveEvent(event);

        event.setTitle("Eminem");

        eventDAO.updateEvent(event);

        long id = event.getId();
        Event eventName = eventDAO.getEvent(id);

        assertEquals("Eminem", eventName.getTitle());

        eventDAO.deleteEvent(event.getId());
        locationDAO.deleteLocation(location.getId());
    }

    @Test
    public void shouldDeleteEvent() throws ParseException {

        Location location = new Location();
        location.setId(1013);
        location.setTitle("Drunk oyster");
        location.setWorkingHours("08:00-22:00");
        location.setType("bar");
        location.setAddress("FooBar street");
        location.setDescription("Bla bla bla for the test");
        location.setCapacityPeople(300);

        locationDAO.deleteLocation(location.getId());

        locationDAO.saveLocation(location);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = "13-08-2021 12:00:00";
        Date concertDate = sdf.parse(dateInString);

        Event event = new Event();
        event.setId(1000);
        event.setTitle("Concert Oxxxymiron");
        event.setDate(concertDate);
        event.setPrice(1500);
        event.setStatus("relevant");
        event.setDescription("Bla bla bla just for test...");
        event.setLocationId(1013);

        eventDAO.deleteEvent(event.getId());

        assertNotNull(eventDAO);
        eventDAO.saveEvent(event);

        eventDAO.deleteEvent(event.getId());
        locationDAO.deleteLocation(location.getId());
    }
}

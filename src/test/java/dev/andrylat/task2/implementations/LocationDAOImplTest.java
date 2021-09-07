package dev.andrylat.task2.implementations;

import dev.andrylat.task2.configs.AppConfig;
import dev.andrylat.task2.dao.LocationDAO;
import dev.andrylat.task2.entities.Location;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
public class LocationDAOImplTest {

    @Autowired
    private LocationDAO locationDAO;

    @Test
    public void shouldReturnLocationById() {

        Location location = new Location();
        location.setId(1013);
        location.setTitle("Drunk oyster");
        location.setWorkingHours("08:00-22:00");
        location.setType("bar");
        location.setAddress("FooBar street");
        location.setDescription("Bla bla bla for the test");
        location.setCapacityPeople(300);

        locationDAO.deleteLocation(location.getId());

        assertNotNull(locationDAO);
        locationDAO.saveLocation(location);

        long id = location.getId();
        Location locationTitle = locationDAO.getLocation(id);

        assertEquals("Drunk oyster", locationTitle.getTitle());
        locationDAO.deleteLocation(location.getId());
    }

    @Test
    public void shouldReturnAllLocations() {

        Location location = new Location();
        location.setId(1013);
        location.setTitle("Drunk oyster");
        location.setWorkingHours("08:00-22:00");
        location.setType("bar");
        location.setAddress("FooBar street");
        location.setDescription("Bla bla bla for the test");
        location.setCapacityPeople(300);

        locationDAO.deleteLocation(location.getId());

        assertNotNull(locationDAO);
        locationDAO.saveLocation(location);

        List<Location> locations = locationDAO.getLocations();

        assertTrue(locations.size() == 1);

        locationDAO.deleteLocation(location.getId());
    }

    @Test
    public void shouldSaveLocationIntoTable() {

        Location location = new Location();
        location.setId(1013);
        location.setTitle("Drunk oyster");
        location.setWorkingHours("08:00-22:00");
        location.setType("bar");
        location.setAddress("FooBar street");
        location.setDescription("Bla bla bla for the test");
        location.setCapacityPeople(300);

        locationDAO.deleteLocation(location.getId());

        assertNotNull(locationDAO);
        locationDAO.saveLocation(location);

        long id = location.getId();
        Location locationTitle = locationDAO.getLocation(id);

        assertEquals("Drunk oyster", locationTitle.getTitle());
        locationDAO.deleteLocation(location.getId());
    }

    @Test
    public void shouldUpdateLocation() {

        Location location = new Location();
        location.setId(1013);
        location.setTitle("Drunk oyster");
        location.setWorkingHours("08:00-22:00");
        location.setType("bar");
        location.setAddress("FooBar street");
        location.setDescription("Bla bla bla for the test");
        location.setCapacityPeople(300);

        locationDAO.deleteLocation(location.getId());

        assertNotNull(locationDAO);
        locationDAO.saveLocation(location);

        location.setId(1013);
        location.setTitle("Oyster");

        locationDAO.updateLocation(location);

        long id = location.getId();
        Location locationTitle = locationDAO.getLocation(id);

        assertEquals("Oyster", locationTitle.getTitle());

        locationDAO.deleteLocation(location.getId());
    }

    @Test
    public void shouldDeleteLocation() {

        Location location = new Location();
        location.setId(1013);
        location.setTitle("Drunk oyster");
        location.setWorkingHours("08:00-22:00");
        location.setType("bar");
        location.setAddress("FooBar street");
        location.setDescription("Bla bla bla for the test");
        location.setCapacityPeople(300);

        locationDAO.deleteLocation(location.getId());

        assertNotNull(locationDAO);
        locationDAO.saveLocation(location);

        locationDAO.deleteLocation(location.getId());
    }
}

package dev.andrylat.task2.dao.impl;

import dev.andrylat.task2.configs.AppConfigTest;
import dev.andrylat.task2.dao.LocationDAO;
import dev.andrylat.task2.entities.Location;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfigTest.class)
@Sql(scripts = {"file:src/test/resources/createTables.sql",
        "file:src/test/resources/populateTables.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "file:src/test/resources/cleanUpTables.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class LocationDAOImplTest {
    private static final String SQL_SELECT_LOCATION_ID = "SELECT location_id " +
            "FROM locations " +
            "WHERE name = ? AND working_hours = ? AND type = ? AND address = ? AND description = ? AND capacity_people = ?";
    private static final String SQL_SELECT_ALL_LOCATIONS_ID = "SELECT location_id FROM locations";

    @Autowired
    private LocationDAO locationDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void getById_ShouldGetLocationById_WhenInputIsId() {

        long id = 100;

        String expectedName = "Drunk oyster";
        String expectedWorkingHours = "08:00-22:00";
        String expectedType = "bar";
        String expectedAddress = "FooBar street";
        String expectedDescription = "description test";
        int expectedCapacityPeople = 300;

        String actualName = locationDAO.getById(id).getTitle();
        String actualWorkingHours = locationDAO.getById(id).getWorkingHours();
        String actualType = locationDAO.getById(id).getType();
        String actualAddress = locationDAO.getById(id).getAddress();
        String actualDescription = locationDAO.getById(id).getDescription();
        int actualCapacityPeople = locationDAO.getById(id).getCapacityPeople();

        assertEquals(expectedName, actualName);
        assertEquals(expectedWorkingHours, actualWorkingHours);
        assertEquals(expectedType, actualType);
        assertEquals(expectedAddress, actualAddress);
        assertEquals(expectedDescription, actualDescription);
        assertEquals(expectedCapacityPeople, actualCapacityPeople);
    }

    @Test
    public void findAll_ShouldGetAllLocations_WhenCallMethod() {

        List<Location> actualLocations = locationDAO.findAll();
        List<Long> expectedId = jdbcTemplate.queryForList(
                SQL_SELECT_ALL_LOCATIONS_ID,
                Long.class
        );
        int expectedSize = expectedId.size();
        int actualSize = actualLocations.size();

        assertEquals(expectedSize, actualSize);

        for (Location location : actualLocations) {
            long actualLocationId = location.getId();

            assertTrue(expectedId.contains(actualLocationId));
        }
    }

    @Test
    public void save_ShouldSaveLocation_WhenInputIsLocationObjectWithDetails() {

        Location newLocation = getLocation(
                102, "Green sleeve",
                "10:00-15:00", "restaurant",
                "Derzhavina str., 13", "The first Irish pub in the city",
                1200
        );

        locationDAO.save(newLocation);

        String checkName = "Green sleeve";
        String checkWorkingHours = "10:00-15:00";
        String checkType = "restaurant";
        String checkAddress = "Derzhavina str., 13";
        String checkDescription = "The first Irish pub in the city";
        int checkCapacityPeople = 1200;

        long expectedId = 102;
        Long actualId = jdbcTemplate.queryForObject(
                SQL_SELECT_LOCATION_ID,
                new Object[]{checkName, checkWorkingHours, checkType,
                        checkAddress, checkDescription, checkCapacityPeople},
                Long.class
        );
        assertEquals(expectedId, actualId);
    }

    @Test
    public void update_ShouldUpdateExistedLocation_WhenInputIsLocationObjectWithDetails() {

        Location updatedLocation = getLocation(
                100, "Green sleeve",
                "10:00-15:00", "restaurant",
                "Derzhavina str., 13", "The first Irish pub in the city",
                1200
        );

        locationDAO.update(updatedLocation);

        String checkName = "Green sleeve";
        String checkWorkingHours = "10:00-15:00";
        String checkType = "restaurant";
        String checkAddress = "Derzhavina str., 13";
        String checkDescription = "The first Irish pub in the city";
        int checkCapacityPeople = 1200;

        long expectedId = 100;
        Long actualId = jdbcTemplate.queryForObject(
                SQL_SELECT_LOCATION_ID,
                new Object[]{checkName, checkWorkingHours, checkType,
                        checkAddress, checkDescription, checkCapacityPeople},
                Long.class
        );
        assertEquals(expectedId, actualId);
    }

    @Test
    public void delete_ShouldDeleteLocationById_WhenInputIsId() {

        long locationId = 100;

        locationDAO.delete(locationId);

        List<Long> actualId = jdbcTemplate.queryForList(
                SQL_SELECT_ALL_LOCATIONS_ID,
                Long.class
        );
        int expectedSize = 1;
        int actualSize = actualId.size();

        int checkedId = 100;

        assertEquals(expectedSize, actualSize);
        assertFalse(actualId.contains(checkedId));
    }

    private Location getLocation(long id, String name,
                                 String workingHours, String type,
                                 String address, String desc,
                                 int capacityPeople) {
        Location location = new Location();
        location.setId(id);
        location.setTitle(name);
        location.setWorkingHours(workingHours);
        location.setType(type);
        location.setAddress(address);
        location.setDescription(desc);
        location.setCapacityPeople(capacityPeople);
        return location;
    }
}


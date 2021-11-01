package dev.andrylat.task2.services.impl;

import dev.andrylat.task2.dao.LocationDAO;
import dev.andrylat.task2.entities.Location;
import dev.andrylat.task2.exceptions.DataNotFoundException;
import dev.andrylat.task2.exceptions.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LocationServiceImplTest {

    @InjectMocks
    private LocationServiceImpl locationService;

    @Mock
    private LocationDAO locationDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getLocationById_ShouldReturnLocationById_WhenInputIsId() {

        Location expectedLocation = getLocation(
                100, "Drunk oyster",
                "08:00-22:00", "bar",
                "FooBar street", "description test", 300);

        Mockito.when(locationDAO.getById(100)).thenReturn(expectedLocation);

        Location actualLocation = locationService.getLocationById(100);

        assertEquals(expectedLocation, actualLocation);
    }

    @Test
    void getLocationById_ShouldThrowServiceException_WhenInputIsIncorrectId() {

        Mockito.when(locationDAO.getById(-1)).thenThrow(ServiceException.class);

        Throwable exception = assertThrows(ServiceException.class,
                () -> locationService.getLocationById(-1));

        String expected = "id can not be less or equals zero";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    void findAllLocations_ShouldReturnAllLocationsSortedByName_WhenInputIsPageRequestWithoutSortValue() {

        Pageable sortedByName = PageRequest.of(0, 2);

        List<Location> expectedLocations = new ArrayList<>();

        Location firstLocation = getLocation(
                100, "Drunk oyster",
                "08:00-22:00", "bar",
                "FooBar street", "description test", 300);

        Location secondLocation = getLocation(
                101, "Moes", "06:00-00:00",
                "tavern", "the great street",
                "description bla bla bla for test", 750);

        expectedLocations.add(firstLocation);
        expectedLocations.add(secondLocation);

        Mockito.when(locationDAO.findAll(sortedByName)).thenReturn(expectedLocations);

        List<Location> actualLocations = locationService.findAllLocations(sortedByName);

        assertTrue(expectedLocations.containsAll(actualLocations));
    }

    @Test
    void findAllLocations_ShouldReturnOneLocationSortedByName_WhenInputIsPageRequestWithSizeOneWithoutSortValue() {

        Pageable sortedByName = PageRequest.of(0, 2);

        List<Location> expectedLocations = new ArrayList<>();

        Location firstLocation = getLocation(
                100, "Drunk oyster",
                "08:00-22:00", "bar",
                "FooBar street", "description test", 300);

        expectedLocations.add(firstLocation);

        Mockito.when(locationDAO.findAll(sortedByName)).thenReturn(expectedLocations);

        List<Location> actualLocations = locationService.findAllLocations(sortedByName);

        assertTrue(expectedLocations.containsAll(actualLocations));
    }

    @Test
    void findAllLocations_ShouldReturnAllLocationsSortedByLocationId_WhenInputIsPageRequestWithSortValue() {

        Pageable sortedById = PageRequest.of(0, 2, Sort.by("location_id"));

        List<Location> expectedLocations = new ArrayList<>();

        Location firstLocation = getLocation(
                100, "Drunk oyster",
                "08:00-22:00", "bar",
                "FooBar street", "description test", 300);

        Location secondLocation = getLocation(
                101, "Moes", "06:00-00:00",
                "tavern", "the great street",
                "description bla bla bla for test", 750);

        expectedLocations.add(firstLocation);
        expectedLocations.add(secondLocation);

        Mockito.when(locationDAO.findAll(sortedById)).thenReturn(expectedLocations);

        List<Location> actualLocations = locationService.findAllLocations(sortedById);

        assertTrue(expectedLocations.containsAll(actualLocations));
    }

    @Test
    void findAllLocations_ShouldGetAllCategoriesSortedByName_WhenPageIsNull() {

        Pageable page = null;

        List<Location> expectedLocations = new ArrayList<>();

        Location firstLocation = getLocation(
                100, "Drunk oyster",
                "08:00-22:00", "bar",
                "FooBar street", "description test", 300);

        Location secondLocation = getLocation(
                101, "Moes", "06:00-00:00",
                "tavern", "the great street",
                "description bla bla bla for test", 750);

        expectedLocations.add(firstLocation);
        expectedLocations.add(secondLocation);

        Mockito.when(locationDAO.findAll(page)).thenReturn(expectedLocations);

        List<Location> actualLocations = locationService.findAllLocations(page);

        assertTrue(expectedLocations.containsAll(actualLocations));
    }

    @Test
    void saveLocation_ShouldSaveNewLocation_WhenInputIsNewLocationWithDetails() {

        Location newLocation = getLocation(
                102, "Green sleeve",
                "10:00-15:00", "restaurant",
                "Derzhavina str., 13", "The first Irish pub in the city",
                1200
        );
        Mockito.when(locationDAO.getById(102)).thenThrow(DataNotFoundException.class);

        locationService.saveLocation(newLocation);

        Mockito.verify(locationDAO, Mockito.times(1)).save(newLocation);
    }

    @Test
    void saveLocation_ShouldThrowServiceException_WhenInputIsHasNegativeId() {

        Location newLocation = getLocation(
                -102, "Green sleeve",
                "10:00-15:00", "restaurant",
                "Derzhavina str., 13", "The first Irish pub in the city",
                1200
        );
        Throwable exception = assertThrows(ServiceException.class,
                () -> locationService.saveLocation(newLocation));

        String expected = "id can not be less or equals zero";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    void saveLocation_ShouldUpdateExistedLocation_WhenInputIsLocationWithDetails() {

        Location oldLocation = getLocation(
                100, "Drunk oyster",
                "08:00-22:00", "bar",
                "FooBar street", "description test", 300
        );
        Location updatedLocation = getLocation(
                100, "Green sleeve",
                "10:00-15:00", "restaurant",
                "Derzhavina str., 13", "The first Irish pub in the city",
                1200
        );
        Mockito.when(locationDAO.getById(100)).thenReturn(oldLocation);

        locationService.saveLocation(updatedLocation);

        Mockito.verify(locationDAO, Mockito.times(1)).update(updatedLocation);
    }

    @Test
    void deleteLocationById_ShouldDeleteLocationById_WhenInputIsId() {

        locationService.deleteLocationById(100);

        Mockito.verify(locationDAO, Mockito.times(1)).delete(100);
    }

    @Test
    void deleteLocationById_ShouldThrowServiceException_WhenInputHasNegativeId() {

        Throwable exception = assertThrows(ServiceException.class,
                () -> locationService.deleteLocationById(-1));

        String expected = "id can not be less or equals zero";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
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


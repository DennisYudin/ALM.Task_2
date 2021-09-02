package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("locationDAO")
public class LocationDAOImpl implements LocationDAO {
    private static final String SQL_SELECT_LOCATION = "SELECT * FROM locations WHERE location_id = ?";
    private static final String SQL_SELECT_ALL_LOCATIONS = "SELECT * FROM locations ORDER BY name";
    private static final String SQL_SAVE_LOCATION = "INSERT INTO locations " +
            "(location_id, name, working_hours, type, address, description, capacity_people)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_LOCATION = "UPDATE locations " +
            "SET name = ?, working_hours = ?, type = ?, address = ?, description = ?, capacity_people = ? " +
            "WHERE location_id = ?";
    private static final String SQL_DELETE_LOCATION = "DELETE FROM locations WHERE location_id = ?";

    private final JdbcTemplate jdbcTemplate;

    public LocationDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Location getLocation(long id) {

        Location location = jdbcTemplate.queryForObject(
                SQL_SELECT_LOCATION,
                new Object[]{id},
                new LocationRowMapper()
        );
        return location;
    }

    @Override
    public List<Location> getLocations() {

        List<Location> locations = jdbcTemplate.query(
                SQL_SELECT_ALL_LOCATIONS,
                new LocationRowMapper()
        );
        return locations;
    }

    @Override
    public void saveLocation(Location location) {

        long id = location.getId();
        String name = location.getTitle();
        String workingHours = location.getWorkingHours();
        String type = location.getType();
        String address = location.getAddress();
        String description = location.getDescription();
        int capacityPeople = location.getCapacityPeople();

        jdbcTemplate.update(
                SQL_SAVE_LOCATION,
                id, name, workingHours, type, address, description, capacityPeople
        );
    }

    @Override
    public void updateLocation(Location location) {

        long id = location.getId();
        String name = location.getTitle();
        String workingHours = location.getWorkingHours();
        String type = location.getType();
        String address = location.getAddress();
        String description = location.getDescription();
        int capacityPeople = location.getCapacityPeople();

        jdbcTemplate.update(
                SQL_UPDATE_LOCATION,
                name, workingHours, type, address, description, capacityPeople, id
        );
    }

    @Override
    public void deleteLocation(long id) {

        jdbcTemplate.update(
                SQL_DELETE_LOCATION,
                id
        );
    }

    @Override
    public Page<Location> sortByName(Sort sort) {

        return null;
    }
}

package dev.andrylat.task2.dao.impl;

import dev.andrylat.task2.dao.LocationDAO;
import dev.andrylat.task2.entities.Location;
import dev.andrylat.task2.mappers.LocationRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public LocationDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    private LocationRowMapper locationRowMapper;

    @Override
    public Location getById(long id) {

        Location location = jdbcTemplate.queryForObject(
                SQL_SELECT_LOCATION,
                locationRowMapper,
                new Object[]{id}
        );
        return location;
    }

    @Override
    public List<Location> findAll() {

        List<Location> locations = jdbcTemplate.query(
                SQL_SELECT_ALL_LOCATIONS,
                locationRowMapper
        );
        return locations;
    }

    @Override
    public void save(Location location) {

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
    public void update(Location location) {

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
    public void delete(long id) {

        jdbcTemplate.update(
                SQL_DELETE_LOCATION,
                id
        );
    }
}

package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.Location;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationDAOImpl implements LocationDAO {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public LocationDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Location getLocationById(long theId) {

        String sqlQuery = "SELECT * FROM locations WHERE location_id = :id";

        final Map<String, Object> namedParameters = new HashMap<>();

        namedParameters.put("id", theId);

        Location location = jdbcTemplate.queryForObject(
                sqlQuery,
                namedParameters,
                new LocationRowMapper());

        return location;
    }

    @Override
    public List<Location> getLocations() {

        String sqlQuery = "SELECT * FROM locations ORDER BY name";

        List<Location> locations = jdbcTemplate.query(
                sqlQuery,
                new LocationRowMapper());

        return locations;
    }

    @Override
    public void saveLocation(Location theLocation) {

        String sqlQuery = "INSERT INTO locations (location_id, name, working_hours, type, " +
                "address, description, capacity_people) " +
                "VALUES (:id, :name, :workingHours, :type, :address, :description, :capacityPeople)";

        long id = theLocation.getId();
        String name = theLocation.getTitle();
        String workingHours = theLocation.getWorkingHours();
        String type = theLocation.getType();
        String address = theLocation.getAddress();
        String description = theLocation.getDescription();
        int capacityPeople = theLocation.getCapacityPeople();

        jdbcTemplate.update(
                sqlQuery,
                Map.of(
                        "id", id,
                        "name", name,
                        "workingHours", workingHours,
                        "type", type,
                        "address", address,
                        "description", description,
                        "capacityPeople", capacityPeople
                )
        );
    }

    @Override
    public void updateLocation(Location theLocation) {

        String sqlQuery = "UPDATE locations SET name = :name, working_hours = :workingHours, " +
                "type = :type, address = :address, description = :description, capacity_people = :capacityPeople " +
                "WHERE location_id = :id";

        long id = theLocation.getId();
        String name = theLocation.getTitle();
        String workingHours = theLocation.getWorkingHours();
        String type = theLocation.getType();
        String address = theLocation.getAddress();
        String description = theLocation.getDescription();
        int capacityPeople = theLocation.getCapacityPeople();

        jdbcTemplate.update(
                sqlQuery,
                Map.of(
                        "id", id,
                        "name", name,
                        "workingHours", workingHours,
                        "type", type,
                        "address", address,
                        "description", description,
                        "capacityPeople", capacityPeople
                )
        );
    }

    @Override
    public void deleteLocation(long theId) {

        String sqlQuery = "DELETE FROM locations WHERE location_id = :id";

        jdbcTemplate.update(
                sqlQuery,
                Map.of(
                        "id", theId
                )
        );
    }
}

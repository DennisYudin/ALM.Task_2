package dev.andrylat.task2.dao.impl;

import dev.andrylat.task2.dao.LocationDAO;
import dev.andrylat.task2.entities.Location;
import dev.andrylat.task2.exceptions.DAOException;
import dev.andrylat.task2.exceptions.DataNotFoundException;
import dev.andrylat.task2.mappers.LocationRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("locationDAO")
public class LocationDAOImpl implements LocationDAO {
    private static final String SQL_SELECT_LOCATION_BY_ID = "SELECT * FROM locations WHERE location_id = ?";
    private static final String SQL_SELECT_ALL_LOCATIONS_ORDER_BY = "SELECT * FROM locations ORDER BY";
    private static final String SQL_SELECT_ALL_LOCATIONS_ORDER_BY_NAME = "SELECT * FROM locations ORDER BY name";
    private static final String SQL_SAVE_LOCATION = "" +
            "INSERT INTO locations " +
            "(location_id, name, working_hours, type, address, description, capacity_people)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_LOCATION = "" +
            "UPDATE locations " +
            "SET name = ?, working_hours = ?, type = ?, address = ?, description = ?, capacity_people = ? " +
            "WHERE location_id = ?";
    private static final String SQL_DELETE_LOCATION = "" +
            "DELETE FROM locations " +
            "WHERE location_id = ?";
    private static final String SORT_BY_COLUMN = "name";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LocationDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    private LocationRowMapper locationRowMapper;

    @Override
    public Location getById(long id) {

        Location location;
        try {
            location = getLocation(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new DataNotFoundException(
                    "There is no such location with id = " + id, ex);
        } catch (DataAccessException ex) {
            throw new DAOException(
                    "Something went wrong when trying to call the method getById()", ex);
        }
        return location;
    }

    private Location getLocation(long id) {
        Location location = jdbcTemplate.queryForObject(
                SQL_SELECT_LOCATION_BY_ID,
                locationRowMapper,
                id
        );
        return location;
    }

    @Override
    public List<Location> findAll(Pageable page) {

        String sqlQuery = getSqlQuery(page);

        List<Location> locations;
        try {
            locations = getLocations(sqlQuery);
        } catch (DataAccessException ex) {
            throw new DAOException(
                    "Something went wrong when trying to call the method findAll()", ex);
        }
        return locations;
    }

    private List<Location> getLocations(String query) {
        List<Location> locations = jdbcTemplate.query(
                query,
                locationRowMapper
        );
        return locations;
    }

    private String getSqlQuery(Pageable pageable) {
        String query = SQL_SELECT_ALL_LOCATIONS_ORDER_BY_NAME;
        if (pageable != null) {
            query = buildSqlQuery(pageable);
        }
        return query;
    }

    private String buildSqlQuery(Pageable pageable) {
        String query;
        if (pageable.getSort().isEmpty()) {
            Sort.Order order = Sort.Order.by(SORT_BY_COLUMN);

            query = collectSqlQuery(pageable, order);
        } else {
            Sort.Order order = pageable.getSort().iterator().next();

            query = collectSqlQuery(pageable, order);
        }
        return query;
    }

    private String collectSqlQuery(Pageable pageable, Sort.Order sort) {

        String sortProperty = sort.getProperty();
        String sortDirectionName = sort.getDirection().name();
        String limit = "LIMIT";
        int pageSize = pageable.getPageSize();
        String offset = "OFFSET";
        long pageOffset = pageable.getOffset();

        String result = String.format(
                SQL_SELECT_ALL_LOCATIONS_ORDER_BY + " %1$s %2$s %3$s %4$d %5$s %6$d",
                sortProperty, sortDirectionName, limit, pageSize, offset, pageOffset);

        return result;
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

        try {
            jdbcTemplate.update(
                    SQL_SAVE_LOCATION,
                    id, name, workingHours, type, address, description, capacityPeople
            );
        } catch (DataAccessException ex) {
            throw new DAOException(
                    "Something went wrong when trying to call the method save()", ex);
        }
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

        try {
            jdbcTemplate.update(
                    SQL_UPDATE_LOCATION,
                    name, workingHours, type, address, description, capacityPeople, id
            );
        } catch (DataAccessException ex) {
            throw new DAOException(
                    "Something went wrong when trying to call the method update()", ex);
        }
    }

    @Override
    public void delete(long id) {
        try {
            jdbcTemplate.update(
                    SQL_DELETE_LOCATION,
                    id
            );
        } catch (DataAccessException ex) {
            throw new DAOException(
                    "Something went wrong when trying to call the method delete()", ex);
        }
    }
}


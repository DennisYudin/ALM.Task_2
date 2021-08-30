package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.Location;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationRowMapper implements RowMapper<Location> {

    @Override
    public Location mapRow(ResultSet rs, int rowNum) throws SQLException {

        Location location = new Location();

        long id = rs.getLong("location_id");
        String name = rs.getString("name");
        String workingHours = rs.getString("working_hours");
        String type = rs.getString("type");
        String address = rs.getString("address");
        String description = rs.getString("description");
        int capacityPeople = rs.getInt("capacity_people");

        location.setId(id);
        location.setTitle(name);
        location.setWorkingHours(workingHours);
        location.setType(type);
        location.setAddress(address);
        location.setDescription(description);
        location.setCapacityPeople(capacityPeople);

        return location;
    }
}

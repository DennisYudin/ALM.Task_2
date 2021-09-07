package dev.andrylat.task2.mappers;

import dev.andrylat.task2.entities.Location;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationRowMapper implements RowMapper<Location> {

    @Override
    public Location mapRow(ResultSet rs, int rowNum) throws SQLException {

        Location location = new Location();

        location.setId(rs.getLong("location_id"));
        location.setTitle(rs.getString("name"));
        location.setWorkingHours(rs.getString("working_hours"));
        location.setType(rs.getString("type"));
        location.setAddress(rs.getString("address"));
        location.setDescription(rs.getString("description"));
        location.setCapacityPeople(rs.getInt("capacity_people"));

        return location;
    }
}

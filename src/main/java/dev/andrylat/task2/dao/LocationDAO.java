package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.Location;

import java.util.List;

public interface LocationDAO {

    Location getLocationById(long theId);
    List<Location> getLocations();
    void saveLocation(Location theLocation);
    void updateLocation(Location theLocation);
    void deleteLocation(long theId);
}

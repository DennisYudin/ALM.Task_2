package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface LocationDAO {

    Location getLocation(long id);
    List<Location> getLocations();
    void saveLocation(Location location);
    void updateLocation(Location location);
    void deleteLocation(long id);

    Page<Location> sortByName(Sort sort);
}

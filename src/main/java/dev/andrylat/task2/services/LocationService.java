package dev.andrylat.task2.services;

import dev.andrylat.task2.entities.Location;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LocationService {

    Location getLocationById(long id);

    List<Location> findAllLocations(Pageable pageable);

    void saveLocation(Location location);

    void deleteLocationById(long id);
}

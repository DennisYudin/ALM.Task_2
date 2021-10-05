package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.Location;

import java.util.List;

public interface LocationDAO extends GenericDAO<Location> {

    Location getById(long id);

    List<Location> findAll();

    void save(Location location);

    void update(Location location);

    void delete(long id);
}

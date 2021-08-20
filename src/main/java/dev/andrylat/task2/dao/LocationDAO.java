package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.Location;

import java.util.List;

public interface LocationDAO {

    List<Location> findAll();
    List<Location> findByTitle(String title);
    void insert(Location location);
    void update(Location location);
    void delete(long locationId);
}

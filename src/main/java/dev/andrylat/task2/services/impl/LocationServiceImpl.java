package dev.andrylat.task2.services.impl;

import dev.andrylat.task2.dao.LocationDAO;
import dev.andrylat.task2.entities.Location;
import dev.andrylat.task2.exceptions.ServiceException;
import dev.andrylat.task2.services.LocationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private static final Logger logger = Logger.getLogger(LocationServiceImpl.class);

    @Autowired
    private LocationDAO locationDAO;

    @Override
    public Location getLocationById(long id) {
        logger.debug("Call method getLocationById() with id = " + id);

        Location location;
        try {
            location = locationDAO.getById(id);
        } catch (Exception ex) {
            logger.error("Could not get location by id = " + id, ex);
            throw new ServiceException("Could not get location by id = " + id, ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Location is" + location.toString());
        }
        return location;
    }

    @Override
    public List<Location> findAllLocations(Pageable pageable) {
        logger.debug("Call method findAllLocations()");

        List<Location> locations;
        try {
            locations = locationDAO.findAll(pageable);
        } catch (Exception ex) {
            logger.error("Could not get locations", ex);
            throw new ServiceException("Could not get locations", ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Locations are " + locations.toString());
        }
        return locations;
    }

    @Override
    public void saveLocation(Location location) {

        Location resultQuery = getLocationById(location.getId());

        if (resultQuery == null) {
            saveNewLocation(location);
        } else {
            updateLocation(location);
        }
    }

    private void saveNewLocation(Location location) {
        logger.debug("Call method saveLocation() for location with id = " + location.getId());

        try {
            locationDAO.save(location);
        } catch (Exception ex) {
            logger.error("Could not save location with id = " + location.getId(), ex);
            throw new ServiceException("Could not save location with id = " + location.getId(), ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(location.toString() + "is added in DB");
        }
    }

    private void updateLocation(Location location) {
        logger.debug("Call method updateLocation() for location with id = " + location.getId());

        try {
            locationDAO.update(location);
        } catch (Exception ex) {
            logger.error("Could not update location with id = " + location.getId(), ex);
            throw new ServiceException("Could not update location with id = " + location.getId(), ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(location.toString() + "is updated in DB");
        }
    }

    @Override
    public void deleteLocationById(long id) {
        logger.debug("Call method deleteLocationById() with id = " + id);

        try {
            locationDAO.delete(id);
        } catch (Exception ex) {
            logger.error("Could not delete location with id = " + id, ex);
            throw new ServiceException("Could not delete location with id = " + id, ex);
        }
        logger.debug("Location with id = " + id + " is deleted in DB");
    }
}
